package capstone.chatservice.domain.server.controller;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageDeleteRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageModifyRequest;
import capstone.chatservice.domain.server.service.command.ServerMessageCommandService;
import capstone.chatservice.infra.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ServerMessageCommandController {

    private final KafkaProducer producerService;
    private final ServerMessageCommandService commandService;

    @MessageMapping("/server/message/send")
    public void save(ServerMessageCreateRequest createRequest) {
        ServerMessageDto messageDto = commandService.save(createRequest);
        producerService.sendToServerChatTopic(messageDto);
    }

    @MessageMapping("/server/message/modify")
    public void modify(ServerMessageModifyRequest modifyRequest) {
        ServerMessageDto messageDto = commandService.modify(modifyRequest);
        producerService.sendToServerChatTopic(messageDto);
    }

    @MessageMapping("/server/message/delete")
    public void modify(ServerMessageDeleteRequest deleteRequest) {
        ServerMessageDto messageDto = commandService.delete(deleteRequest);
        producerService.sendToServerChatTopic(messageDto);
    }
}
