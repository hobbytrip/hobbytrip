package capstone.chatservice.domain.forum.controller;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.request.ForumMessageCreateRequest;
import capstone.chatservice.domain.forum.service.ForumMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ForumMessageController {

    private final ForumMessageService forumMessageService;

    @MessageMapping("/forum/message/send")
    public void save(ForumMessageCreateRequest createRequest) {
        ForumMessageDto messageDto = forumMessageService.save(createRequest);
    }
}
