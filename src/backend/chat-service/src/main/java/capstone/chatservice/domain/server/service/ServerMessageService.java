package capstone.chatservice.domain.server.service;

import capstone.chatservice.domain.server.domain.ServerMessage;
import capstone.chatservice.domain.server.dto.ServerMessageDto;
import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageDeleteRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageModifyRequest;
import capstone.chatservice.domain.server.repository.ServerMessageRepository;
import capstone.chatservice.global.util.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerMessageService {

    private final SequenceGenerator sequenceGenerator;
    private final ServerMessageRepository messageRepository;

    @Transactional
    public ServerMessageDto save(ServerMessageCreateRequest createRequest) {
        ServerMessage serverMessage = ServerMessage.builder()
                .serverId(createRequest.getServerId())
                .channelId(createRequest.getChannelId())
                .userId(createRequest.getUserId())
                .parentId(createRequest.getParentId())
                .profileImage(createRequest.getProfileImage())
                .type(createRequest.getType())
                .content(createRequest.getContent())
                .writer(createRequest.getWriter())
                .build();

        serverMessage.generateSequence(sequenceGenerator.generateSequence(ServerMessage.SEQUENCE_NAME));

        return ServerMessageDto.from(messageRepository.save(serverMessage));
    }

    @Transactional
    public ServerMessageDto modify(ServerMessageModifyRequest modifyRequest) {
        ServerMessage serverMessage = messageRepository.findById(modifyRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException("no message"));

        serverMessage.modify(modifyRequest.getType(), modifyRequest.getContent());

        return ServerMessageDto.from(messageRepository.save(serverMessage));
    }

    @Transactional
    public ServerMessageDto delete(ServerMessageDeleteRequest deleteRequest) {
        ServerMessage serverMessage = messageRepository.findById(deleteRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException("no message"));

        serverMessage.delete(deleteRequest.getType());

        return ServerMessageDto.from(messageRepository.save(serverMessage));
    }
}