package capstone.chatservice.domain.dm.controller;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.dm.dto.request.DirectMessageCreateRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageModifyRequest;
import capstone.chatservice.domain.dm.service.DirectMessageService;
import capstone.chatservice.infra.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DirectMessageController {

    private final KafkaProducer kafkaProducer;
    private final DirectMessageService directMessageService;

    @MessageMapping("/direct/message/send")
    public void save(DirectMessageCreateRequest createRequest) {

        DirectMessageDto messageDto = directMessageService.save(createRequest);
        kafkaProducer.sendToDirectChatTopic(messageDto);
    }

    @MessageMapping("/direct/message/modify")
    public void modify(DirectMessageModifyRequest modifyRequest) {

        DirectMessageDto messageDto = directMessageService.modify(modifyRequest);
        kafkaProducer.sendToDirectChatTopic(messageDto);
    }
}
