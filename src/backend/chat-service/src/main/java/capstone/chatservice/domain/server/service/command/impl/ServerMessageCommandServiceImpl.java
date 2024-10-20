package capstone.chatservice.domain.server.service.command.impl;

import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import capstone.chatservice.domain.server.domain.ServerMessage;
import capstone.chatservice.domain.server.dto.event.ServerChatCreateEvent;
import capstone.chatservice.domain.server.dto.event.ServerChatDeleteEvent;
import capstone.chatservice.domain.server.dto.event.ServerChatModifyEvent;
import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageDeleteRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageModifyRequest;
import capstone.chatservice.domain.server.exception.ServerChatException;
import capstone.chatservice.domain.server.repository.ServerMessageRepository;
import capstone.chatservice.domain.server.service.command.ServerMessageCommandService;
import capstone.chatservice.global.event.Events;
import capstone.chatservice.global.exception.Code;
import capstone.chatservice.global.util.SequenceGenerator;
import capstone.chatservice.global.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerMessageCommandServiceImpl implements ServerMessageCommandService {

    private final SequenceGenerator sequenceGenerator;
    private final ServerMessageRepository messageRepository;

    @Override
    public void save(ServerMessageCreateRequest createRequest) {
        ServerMessage serverMessage = ServerMessage.builder()
                .serverId(createRequest.getServerId())
                .channelId(createRequest.getChannelId())
                .userId(createRequest.getUserId())
                .parentId(createRequest.getParentId())
                .profileImage(createRequest.getProfileImage())
                .content(createRequest.getContent())
                .writer(createRequest.getWriter())
                .chatType(ChatType.SERVER)
                .actionType(ActionType.SEND)
                .files(createRequest.getFiles())
                .build();

        serverMessage.generateSequence(sequenceGenerator.generateSequence(ServerMessage.SEQUENCE_NAME));
        ServerChatCreateEvent chatCreateEvent = ServerChatCreateEvent.from(messageRepository.save(serverMessage),
                UUIDUtil.generateUUID());
        Events.send(chatCreateEvent);
    }

    @Override
    public void modify(ServerMessageModifyRequest modifyRequest) {
        ServerMessage serverMessage = messageRepository.findById(modifyRequest.getMessageId())
                .orElseThrow(() -> new ServerChatException(Code.NOT_FOUND));

        serverMessage.modify(modifyRequest.getContent());

        ServerChatModifyEvent chatModifyEvent = ServerChatModifyEvent.from(messageRepository.save(serverMessage),
                UUIDUtil.generateUUID());
        Events.send(chatModifyEvent);
    }

    @Override
    public void delete(ServerMessageDeleteRequest deleteRequest) {
        ServerMessage serverMessage = messageRepository.findById(deleteRequest.getMessageId())
                .orElseThrow(() -> new ServerChatException(Code.NOT_FOUND));

        serverMessage.delete();

        ServerChatDeleteEvent chatDeleteEvent = ServerChatDeleteEvent.from(messageRepository.save(serverMessage),
                UUIDUtil.generateUUID());
        Events.send(chatDeleteEvent);
    }
}
