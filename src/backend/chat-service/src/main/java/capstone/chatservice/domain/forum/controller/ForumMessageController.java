package capstone.chatservice.domain.forum.controller;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.request.ForumMessageCreateRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageModifyRequest;
import capstone.chatservice.domain.forum.service.ForumMessageService;
import capstone.chatservice.infra.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ForumMessageController {

    private final KafkaProducer kafkaProducer;
    private final ForumMessageService forumMessageService;

    @MessageMapping("/forum/message/send")
    public void save(ForumMessageCreateRequest createRequest) {
        ForumMessageDto messageDto = forumMessageService.save(createRequest);
        kafkaProducer.sendToForumChatTopic(messageDto);
    }

    @MessageMapping("/forum/message/modify")
    public void modify(ForumMessageModifyRequest modifyRequest) {
        ForumMessageDto messageDto = forumMessageService.modify(modifyRequest);
        kafkaProducer.sendToForumChatTopic(messageDto);
    }
}
