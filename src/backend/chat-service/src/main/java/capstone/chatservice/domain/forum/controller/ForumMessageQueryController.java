package capstone.chatservice.domain.forum.controller;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.request.ForumMessageTypingRequest;
import capstone.chatservice.domain.forum.dto.response.ForumChannelResponseDto;
import capstone.chatservice.domain.forum.service.query.ForumMessageQueryService;
import capstone.chatservice.global.common.dto.DataResponseDto;
import capstone.chatservice.global.common.dto.PageResponseDto;
import capstone.chatservice.infra.kafka.producer.chat.ChatEventProducer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ForumMessageQueryController {

    private final ChatEventProducer chatEventProducer;
    private final ForumMessageQueryService queryService;

    @GetMapping("/feign/forum/messages/count")
    public ForumChannelResponseDto getForumsMessageCount(@RequestParam(value = "forumIds") List<Long> forumIds) {

        return queryService.getForumsMessageCount(forumIds);
    }

    @GetMapping("/feign/forum/messages/forum")
    public Page<ForumMessageDto> getFeignMessages(@RequestParam(value = "forumId") Long forumId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "30") int size) {

        return queryService.getMessages(forumId, page, size);
    }

    @GetMapping("/forum/messages/forum")
    public DataResponseDto<Object> getMessages(@RequestParam(value = "forumId") Long forumId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size) {

        Page<ForumMessageDto> messages = queryService.getMessages(forumId, page, size);
        PageResponseDto pageResponseDto = PageResponseDto.of(messages);
        return DataResponseDto.of(pageResponseDto);
    }

    @GetMapping("/forum/comments/message")
    public DataResponseDto<Object> getComments(@RequestParam(value = "parentId") Long parentId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size) {

        Page<ForumMessageDto> comments = queryService.getComments(parentId, page, size);
        PageResponseDto pageResponseDto = PageResponseDto.of(comments);
        return DataResponseDto.of(pageResponseDto);
    }

    @MessageMapping("/forum/message/typing")
    public void typing(ForumMessageTypingRequest typingRequest) {
        ForumMessageDto forumMessageDto = ForumMessageDto.from(typingRequest);
        chatEventProducer.sendToForumChatTopic(forumMessageDto);
    }
}
