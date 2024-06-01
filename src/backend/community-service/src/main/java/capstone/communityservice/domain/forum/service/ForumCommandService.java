package capstone.communityservice.domain.forum.service;

import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import capstone.communityservice.domain.channel.exception.ChannelException;
import capstone.communityservice.domain.channel.repository.ChannelRepository;
import capstone.communityservice.domain.forum.dto.*;
import capstone.communityservice.domain.forum.entity.File;
import capstone.communityservice.domain.forum.entity.Forum;
import capstone.communityservice.domain.forum.entity.ForumCategory;
import capstone.communityservice.domain.forum.exception.ForumException;
import capstone.communityservice.domain.forum.repository.FileRepository;
import capstone.communityservice.domain.forum.repository.ForumRepository;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.domain.server.service.ServerQueryService;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.service.UserQueryService;
import capstone.communityservice.global.common.dto.kafka.CommunityForumEventDto;
import capstone.communityservice.global.common.dto.kafka.CommunityServerEventDto;
import capstone.communityservice.global.common.service.FileUploadService;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ForumCommandService {
    private static final String forumKafkaTopic = "communityForumEventTopic";

    private final FileUploadService fileUploadService;
    private final KafkaTemplate<String, CommunityForumEventDto> forumKafkaTemplate;

    private final UserQueryService userQueryService;
    private final ServerQueryService serverQueryService;

    private final ForumRepository forumRepository;
    private final ChannelRepository channelRepository;
    private final FileRepository fileRepository;
    private final ServerRepository serverRepository;

    // 마크다운을 읽으려면 XSS 보안 처리를 해줘야 함
    public ForumCreateResponseDto create(ForumCreateRequestDto requestDto, List<MultipartFile> fileList) {
        validateServerAndChannel(requestDto.getServerId(), requestDto.getChannelId());

        User findUser = userQueryService.findUserByOriginalId(
                requestDto.getUserId()
        );

        Forum newForum = forumRepository.save(
                Forum.of(
                        requestDto.getChannelId(),
                        requestDto.getTitle(),
                        findUser,
                        requestDto.getContent(),
                        requestDto.getForumCategory()
                )
        );

        fileList.stream().forEach(System.out :: println);

        List<FileResponseDto> files = fileList != null ? uploadFile(fileList, newForum).stream()
                .map(FileResponseDto::of)
                .toList() : null;

        forumKafkaTemplate.send(
                forumKafkaTopic,
                CommunityForumEventDto.of("forum-create",
                        requestDto.getServerId(),
                        newForum,
                        requestDto.getChannelId(),
                        files
                )
        );
        printKafkaLog("create");
        return ForumCreateResponseDto.of(newForum, findUser.getName());
    }

    public ForumUpdateResponseDto update(ForumUpdateRequestDto requestDto, List<Long> filesId, List<MultipartFile> fileList){
        validateServerAndChannel(requestDto.getServerId(), requestDto.getChannelId());

        Forum findForum = validateExistForum(requestDto.getForumId());

        validateOwnerUser(findForum, requestDto.getUserId());

        List<FileResponseDto> files = getFileResponseDtos(
                requestDto,
                filesId,
                fileList,
                findForum
        );

        modifyForum(
                findForum,
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getCategory()
        );


        forumKafkaTemplate.send(
                forumKafkaTopic,
                CommunityForumEventDto.of("forum-update",
                        requestDto.getServerId(),
                        findForum,
                        requestDto.getChannelId(),
                        files
                )
        );

        printKafkaLog("update");

        return ForumUpdateResponseDto.of(findForum, files);
    }

    public void delete(ForumDeleteRequestDto requestDto){
        validateServerAndChannel(requestDto.getServerId(), requestDto.getChannelId());
        Forum findForum = validateExistForum(requestDto.getForumId());

        validateDelete(findForum, requestDto.getUserId(), requestDto.getChannelId());

        forumKafkaTemplate.send(
                forumKafkaTopic,
                CommunityForumEventDto.of("forum-delete",
                        requestDto.getServerId(),
                        requestDto.getChannelId(),
                        findForum.getId()
                )
        );
        printKafkaLog("delete");

        forumRepository.delete(findForum);
    }

    private void validateServerAndChannel(Long serverId, Long channelId) {
        validateExistServer(serverId);
        validateForumChannel(channelId);
    }

    private void validateDelete(Forum forum, Long userId, Long channelId) {
        if(!Objects.equals(forum.getUser().getId(), userId) &&
                 !channelRepository.validateChannelManager(channelId).equals(userId)
        ){
            throw new ForumException(Code.VALIDATION_ERROR, "Not Forum Owner");
        }

        forum.getFiles().get(0);

        fileDelete(forum.getFiles());
    }

    private List<File> updateFiles(List<Long> filesId, List<MultipartFile> fileList, Forum forum) {
        if(filesId != null){
            List<File> files = fileRepository.findAllById(filesId);

            fileDelete(files);

            // List<File> files = file != null ? uploadProfile(fileList, newForum) : null; <- S3 등록 후

            return uploadFile(fileList, forum);
        }
        return null;
    }

    private void fileDelete(List<File> files) {
        files.stream()
                .map(File::getFileUrl)
                .forEach(fileUploadService::delete);

        fileRepository.deleteAll(files);
    }

    private Forum validateExistForum(Long forumId){
        return forumRepository.findById(forumId)
                .orElseThrow(() ->
                        new ForumException(Code.NOT_FOUND, "Forum Not Found")
                );
    }

    private void validateOwnerUser(Forum forum, Long userId){
        if(!Objects.equals(forum.getUser().getId(), userId)){
            throw new ForumException(Code.VALIDATION_ERROR, "Not Forum Owner");
        }
    }

    private List<File> uploadFile(List<MultipartFile> fileList, Forum forum) {
        List<File> files = new ArrayList<>();

        for(MultipartFile file : fileList){
            String fileUrl = fileUploadService.save(file);
            File newFile = File.of(forum, fileUrl);
            fileRepository.save(newFile);

            files.add(newFile);
        }

        return files;
    }

    private void validateForumChannel(Long channelId) {
        Channel findChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(
                        Code.NOT_FOUND, "Not Found Channel")
                );

        if(!findChannel.getChannelType()
                .equals(ChannelType.FORUM))
        {
            throw new ForumException(Code.VALIDATION_ERROR, "Not Forum Channel");
        }
    }
    private void validateExistServer(Long serverId){
        serverRepository.findById(serverId)
                .orElseThrow(() ->
                        new ServerException(Code.NOT_FOUND, "Server Not Found")
                );
    }

    private List<FileResponseDto> getFileResponseDtos(ForumUpdateRequestDto requestDto, List<Long> filesId, List<MultipartFile> fileList, Forum findForum) {
        List<File> response = updateFiles(
                filesId,
                fileList,
                findForum
        );

        List<FileResponseDto> files;

        if(response != null) {
            files = response.
                    stream().
                    map(FileResponseDto::of).
                    toList();
        } else {
            files = null;
        }

        return files;
    }

    private void modifyForum(Forum forum, String title, String content, ForumCategory category){
        forum.setForum(
                title,
                content
        );

        if(category != null){
            forum.setCategory(category);
        }
    }

    private void printKafkaLog(String type) {
        log.info("Kafka event send about Forum {}", type);
    }
}
