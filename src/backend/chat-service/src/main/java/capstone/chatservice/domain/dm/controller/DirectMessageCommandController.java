package capstone.chatservice.domain.dm.controller;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.dm.dto.request.DirectMessageCreateRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageDeleteRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageModifyRequest;
import capstone.chatservice.domain.dm.service.command.DirectMessageCommandService;
import capstone.chatservice.infra.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DirectMessageCommandController {

    private final KafkaProducer kafkaProducer;
    private final DirectMessageCommandService commandService;

    @MessageMapping("/direct/message/send")
    public void save(DirectMessageCreateRequest createRequest) {

        DirectMessageDto messageDto = commandService.save(createRequest);
        kafkaProducer.sendToDirectChatTopic(messageDto);
    }

    @MessageMapping("/direct/message/modify")
    public void modify(DirectMessageModifyRequest modifyRequest) {

        DirectMessageDto messageDto = commandService.modify(modifyRequest);
        kafkaProducer.sendToDirectChatTopic(messageDto);
    }

    @MessageMapping("/direct/message/delete")
    public void modify(DirectMessageDeleteRequest deleteRequest) {

        DirectMessageDto messageDto = commandService.delete(deleteRequest);
        kafkaProducer.sendToDirectChatTopic(messageDto);
    }
}