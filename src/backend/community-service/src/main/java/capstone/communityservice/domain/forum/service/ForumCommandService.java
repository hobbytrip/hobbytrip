package capstone.communityservice.domain.forum.service;

import capstone.communityservice.domain.channel.exception.ChannelException;
import capstone.communityservice.domain.channel.repository.ChannelRepository;
import capstone.communityservice.domain.forum.dto.*;
import capstone.communityservice.domain.forum.entity.File;
import capstone.communityservice.domain.forum.entity.Forum;
import capstone.communityservice.domain.forum.exception.ForumException;
import capstone.communityservice.domain.forum.repository.FileRepository;
import capstone.communityservice.domain.forum.repository.ForumRepository;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.service.UserQueryService;
import capstone.communityservice.global.common.service.FileUploadService;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserQueryService userQueryService;
    private final FileUploadService fileUploadService;

    private final ForumRepository forumRepository;
    private final ChannelRepository channelRepository;
    private final FileRepository fileRepository;

    public ForumCreateResponseDto create(ForumCreateRequestDto requestDto, List<MultipartFile> fileList) {
        validateExistChannel(requestDto.getChannelId());

        User findUser = userQueryService.findUserByOriginalId(
                requestDto.getUserId()
        );

        Forum newForum = forumRepository.save(
                Forum.of(
                        requestDto.getChannelId(),
                        requestDto.getTitle(),
                        requestDto.getUserId(),
                        requestDto.getContent()
                )
        );

        // List<File> files = file != null ? uploadProfile(fileList, newForum) : null; <- S3 등록 후
        uploadFile(fileList, newForum);

        return ForumCreateResponseDto.of(newForum, findUser.getName());
    }

    public ForumUpdateResponseDto update(ForumUpdateRequestDto requestDto, List<Long> filesId, List<MultipartFile> fileList){
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

        return ForumUpdateResponseDto.of(findForum, files);
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
    }

    private void validateExistChannel(Long channelId){
        channelRepository.findById(channelId)
                .orElseThrow(() ->
                        new ChannelException(Code.NOT_FOUND, "Channel Not Found")
                );
    }

    private Forum validateExistForum(Long forumId){
        return forumRepository.findById(forumId)
                .orElseThrow(() ->
                        new ForumException(Code.NOT_FOUND, "Forum Not Found")
                );
    }

    private void validateOwnerUser(Forum forum, Long userId){
        if(!Objects.equals(forum.getUserId(), userId)){
            throw new ForumException(Code.VALIDATION_ERROR, "Not Forum Owner");
        } else{
            userQueryService.findUserByOriginalId(userId);
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

        files.add(newFile);
        return files;
    }
}
