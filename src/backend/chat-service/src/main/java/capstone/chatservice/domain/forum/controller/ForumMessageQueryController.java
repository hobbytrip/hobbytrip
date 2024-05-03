package capstone.chatservice.domain.forum.controller;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.service.query.ForumMessageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ForumMessageQueryController {

    private final ForumMessageQueryService queryService;

    @GetMapping("/api/chat/forum/messages/forum")
    public Page<ForumMessageDto> getMessages(@RequestParam(value = "forumId") Long forumId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "30") int size) {

        return queryService.getMessages(forumId, page, size);
    }

    @GetMapping("/api/chat/forum/comments/message")
    public Page<ForumMessageDto> getComments(@RequestParam(value = "parentId") Long parentId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "30") int size) {

        return queryService.getComments(parentId, page, size);
    }
}