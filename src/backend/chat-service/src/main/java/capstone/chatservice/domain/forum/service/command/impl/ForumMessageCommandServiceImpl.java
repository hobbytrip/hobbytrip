package capstone.chatservice.domain.forum.service.command.impl;

import capstone.chatservice.domain.forum.domain.ForumMessage;
import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.request.ForumMessageCreateRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageDeleteRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageModifyRequest;
import capstone.chatservice.domain.forum.repository.ForumMessageRepository;
import capstone.chatservice.domain.forum.service.command.ForumMessageCommandService;
import capstone.chatservice.global.util.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ForumMessageCommandServiceImpl implements ForumMessageCommandService {

    private final SequenceGenerator sequenceGenerator;
    private final ForumMessageRepository forumMessageRepository;

    @Override
    public ForumMessageDto save(ForumMessageCreateRequest createRequest) {
        ForumMessage forumMessage = ForumMessage.builder()
                .serverId(createRequest.getServerId())
                .forumId(createRequest.getForumId())
                .channelId(createRequest.getChannelId())
                .userId(createRequest.getUserId())
                .parentId(createRequest.getParentId())
                .profileImage(createRequest.getProfileImage())
                .type(createRequest.getType())
                .content(createRequest.getContent())
                .writer(createRequest.getWriter())
                .files(createRequest.getFiles())
                .build();

        forumMessage.generateSequence(sequenceGenerator.generateSequence(ForumMessage.SEQUENCE_NAME));

        return ForumMessageDto.from(forumMessageRepository.save(forumMessage));
    }

    @Override
    public ForumMessageDto modify(ForumMessageModifyRequest modifyRequest) {
        ForumMessage forumMessage = forumMessageRepository.findById(modifyRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException("no message"));

        forumMessage.modify(modifyRequest.getType(), modifyRequest.getContent());

        return ForumMessageDto.from(forumMessageRepository.save(forumMessage));
    }

    @Override
    public ForumMessageDto delete(ForumMessageDeleteRequest deleteRequest) {
        ForumMessage forumMessage = forumMessageRepository.findById(deleteRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException("no message"));

        forumMessage.delete(deleteRequest.getType());

        return ForumMessageDto.from(forumMessageRepository.save(forumMessage));
    }
}
