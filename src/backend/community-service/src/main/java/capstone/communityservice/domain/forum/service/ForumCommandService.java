package capstone.communityservice.domain.forum.service;

import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import capstone.communityservice.domain.channel.exception.ChannelException;
import capstone.communityservice.domain.channel.repository.ChannelRepository;
import capstone.communityservice.domain.forum.dto.*;
import capstone.communityservice.domain.forum.entity.File;
import capstone.communityservice.domain.forum.entity.Forum;
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
                        requestDto.getContent()
                )
        );

        // List<File> files = file != null ? uploadProfile(fileList, newForum) : null; <- S3 등록 후
        List<FileResponseDto> files = uploadFile(fileList, newForum).stream()
                .map(FileResponseDto::of)
                .toList();

//        serverKafkaTemplate.send(serverKafkaTopic, CommunityServerEventDto.of("server-update", findServer));
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

        List<FileResponseDto> files = updateFiles(filesId, fileList, findForum).
                stream().
                map(FileResponseDto::of).
                toList();

        findForum.setForum(
                requestDto.getTitle(),
                requestDto.getContent()
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
    }

    private List<File> updateFiles(List<Long> filesId, List<MultipartFile> fileList, Forum forum) {
        if(filesId != null){
            fileRepository.deleteAll(
                    fileRepository.findAllById(filesId)
            );
            // List<File> files = file != null ? uploadProfile(fileList, newForum) : null; <- S3 등록 후
            List<File> files = uploadFile(fileList, forum);

            return files;
        }
        return null;
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

//        for(MultipartFile file : fileList){
//            String fileUrl = fileUploadService.save(file);
//            File newFile = File.of(forum, fileUrl);
//            fileRepository.save(newFile);
//
//            files.add(newFile);
//        }

        // return files; <- S3 등록 후

        File newFile = File.of(forum, "http://image.png");
        fileRepository.save(newFile);

        File newFile2 = File.of(forum, "http://image.png");
        fileRepository.save(newFile2);

        files.add(newFile);
        files.add(newFile2);

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

    private void printKafkaLog(String type) {
        log.info("Kafka event send about Forum {}", type);
    }
}
