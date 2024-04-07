package capstone.chatservice.domain.server.controller;

import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageModifyRequest;
import capstone.chatservice.domain.server.dto.response.ServerMessageCreateResponse;
import capstone.chatservice.domain.server.dto.response.ServerMessageModifyResponse;
import capstone.chatservice.domain.server.service.ServerMessageService;
import capstone.chatservice.infra.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServerMessageController {

    private final ServerMessageService messageService;
    private final KafkaProducerService producerService;

    @MessageMapping("/server/message/send")
    public void save(ServerMessageCreateRequest createRequest) {
        ServerMessageCreateResponse messageDto = messageService.save(createRequest);
        producerService.sendToMultiServerChatTopic(messageDto);
    }

    @MessageMapping("/server/message/modify")
    public void modify(ServerMessageModifyRequest modifyRequest) {
        ServerMessageModifyResponse modifyResponse = messageService.modify(modifyRequest);
        producerService.sendToMultiServerChatTopic(modifyResponse);
    }
}