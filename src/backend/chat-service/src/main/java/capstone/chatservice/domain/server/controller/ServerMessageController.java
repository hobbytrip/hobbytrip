package capstone.chatservice.domain.server.controller;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageDeleteRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageModifyRequest;
import capstone.chatservice.domain.server.service.ServerMessageService;
import capstone.chatservice.infra.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServerMessageController {

    private final ServerMessageService messageService;
    private final KafkaProducer producerService;

    @MessageMapping("/server/message/send")
    public void save(ServerMessageCreateRequest createRequest) {
        ServerMessageDto messageDto = messageService.save(createRequest);
        producerService.sendToServerChatTopic(messageDto);
    }

    @MessageMapping("/server/message/modify")
    public void modify(ServerMessageModifyRequest modifyRequest) {
        ServerMessageDto messageDto = messageService.modify(modifyRequest);
        producerService.sendToServerChatTopic(messageDto);
    }

    @MessageMapping("/server/message/delete")
    public void modify(ServerMessageDeleteRequest deleteRequest) {
        ServerMessageDto messageDto = messageService.delete(deleteRequest);
        producerService.sendToServerChatTopic(messageDto);
    }

    @GetMapping("/api/chat/server/messages/channel")
    public Page<ServerMessageDto> getMessages(@RequestParam(value = "channelId") Long channelId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "30") int size) {

        return messageService.getMessages(channelId, page, size);
    }

}