package capstone.chatservice.domain.dm.controller;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.dm.dto.request.DirectMessageTypingRequest;
import capstone.chatservice.domain.dm.service.query.DirectMessageQueryService;
import capstone.chatservice.global.common.dto.DataResponseDto;
import capstone.chatservice.global.common.dto.PageResponseDto;
import capstone.chatservice.infra.kafka.producer.chat.ChatEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DirectMessageQueryController {

    private final ChatEventProducer chatEventProducer;
    private final DirectMessageQueryService queryService;

    @GetMapping("/feign/direct/messages/room")
    public Page<DirectMessageDto> getFeignMessages(@RequestParam(value = "roomId") Long roomId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "30") int size) {

        return queryService.getDirectMessages(roomId, page, size);

    }

    @GetMapping("/direct/messages/room")
    public DataResponseDto<Object> getMessages(@RequestParam(value = "roomId") Long roomId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size) {

        Page<DirectMessageDto> directMessages = queryService.getDirectMessages(roomId, page, size);
        PageResponseDto pageResponseDto = PageResponseDto.of(directMessages);
        return DataResponseDto.of(pageResponseDto);
    }

    @GetMapping("/direct/comments")
    public DataResponseDto<Object> getComments(@RequestParam(value = "parentId") Long parentId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size) {

        Page<DirectMessageDto> comments = queryService.getComments(parentId, page, size);
        PageResponseDto pageResponseDto = PageResponseDto.of(comments);
        return DataResponseDto.of(pageResponseDto);
    }

    @MessageMapping("/direct/message/typing")
    public void typing(DirectMessageTypingRequest typingRequest) {
        DirectMessageDto directMessageDto = DirectMessageDto.from(typingRequest);
        chatEventProducer.sendToDirectChatTopic(directMessageDto);
    }
}
