package capstone.chatservice.domain.forum.controller;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.request.ForumMessageCreateRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageDeleteRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageModifyRequest;
import capstone.chatservice.domain.forum.service.ForumMessageService;
import capstone.chatservice.infra.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @MessageMapping("/forum/message/delete")
    public void delete(ForumMessageDeleteRequest deleteRequest) {
        ForumMessageDto messageDto = forumMessageService.delete(deleteRequest);
        kafkaProducer.sendToForumChatTopic(messageDto);
    }

    @GetMapping("/api/chat/forum/messages/forum")
    public Page<ForumMessageDto> getMessages(@RequestParam(value = "forumId") Long forumId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "30") int size) {

        return forumMessageService.getMessages(forumId, page, size);
    }

    @GetMapping("/api/chat/forum/comments/message")
    public Page<ForumMessageDto> getComments(@RequestParam(value = "parentId") Long parentId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "30") int size) {

        return forumMessageService.getComments(parentId, page, size);
    }
}
