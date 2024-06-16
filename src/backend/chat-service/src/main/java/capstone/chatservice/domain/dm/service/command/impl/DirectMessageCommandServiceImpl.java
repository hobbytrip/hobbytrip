package capstone.chatservice.domain.dm.service.command.impl;

import capstone.chatservice.domain.dm.domain.DirectMessage;
import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.dm.dto.request.DirectMessageCreateRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageDeleteRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageModifyRequest;
import capstone.chatservice.domain.dm.exception.DmChatException;
import capstone.chatservice.domain.dm.repository.DirectMessageRepository;
import capstone.chatservice.domain.dm.service.command.DirectMessageCommandService;
import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import capstone.chatservice.global.exception.Code;
import capstone.chatservice.global.util.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DirectMessageCommandServiceImpl implements DirectMessageCommandService {

    private final SequenceGenerator sequenceGenerator;
    private final DirectMessageRepository messageRepository;

    @Override
    public DirectMessageDto save(DirectMessageCreateRequest createRequest) {
        DirectMessage directMessage = DirectMessage.builder()
                .dmRoomId(createRequest.getDmRoomId())
                .parentId(createRequest.getParentId())
                .userId(createRequest.getUserId())
                .content(createRequest.getContent())
                .writer(createRequest.getWriter())
                .profileImage(createRequest.getProfileImage())
                .chatType(ChatType.DM)
                .actionType(ActionType.SEND)
                .files(createRequest.getFiles())
                .build();

        directMessage.generateSequence(sequenceGenerator.generateSequence(DirectMessage.SEQUENCE_NAME));

        return DirectMessageDto.from(messageRepository.save(directMessage));
    }

    @Override
    public DirectMessageDto modify(DirectMessageModifyRequest modifyRequest) {
        DirectMessage directMessage = messageRepository.findById(modifyRequest.getMessageId())
                .orElseThrow(() -> new DmChatException(Code.NOT_FOUND));

        directMessage.modify(modifyRequest.getContent());

        return DirectMessageDto.from(messageRepository.save(directMessage));
    }

    @Override
    public DirectMessageDto delete(DirectMessageDeleteRequest deleteRequest) {
        DirectMessage directMessage = messageRepository.findById(deleteRequest.getMessageId())
                .orElseThrow(() -> new DmChatException(Code.NOT_FOUND));

        directMessage.delete();

        return DirectMessageDto.from(messageRepository.save(directMessage));
    }
}
