package capstone.chatservice.domain.dm.controller;

import capstone.chatservice.domain.dm.dto.request.DirectMessageCreateRequest;
import capstone.chatservice.domain.dm.service.DirectMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageService directMessageService;

    @MessageMapping("/direct/message/send")
    public void save(DirectMessageCreateRequest createRequest) {

        directMessageService.save(createRequest);
    }
}
