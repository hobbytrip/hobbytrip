package capstone.chatservice.domain.forum.controller;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.request.ForumMessageCreateRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageDeleteRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageModifyRequest;
import capstone.chatservice.domain.forum.service.command.ForumMessageCommandService;
import capstone.chatservice.infra.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ForumMessageCommandController {

    private final KafkaProducer kafkaProducer;
    private final ForumMessageCommandService commandService;

    @MessageMapping("/forum/message/send")
    public void save(ForumMessageCreateRequest createRequest) {
        ForumMessageDto messageDto = commandService.save(createRequest);
        kafkaProducer.sendToForumChatTopic(messageDto);
    }

    @MessageMapping("/forum/message/modify")
    public void modify(ForumMessageModifyRequest modifyRequest) {
        ForumMessageDto messageDto = commandService.modify(modifyRequest);
        kafkaProducer.sendToForumChatTopic(messageDto);
    }

    @MessageMapping("/forum/message/delete")
    public void delete(ForumMessageDeleteRequest deleteRequest) {
        ForumMessageDto messageDto = commandService.delete(deleteRequest);
        kafkaProducer.sendToForumChatTopic(messageDto);
    }
}
