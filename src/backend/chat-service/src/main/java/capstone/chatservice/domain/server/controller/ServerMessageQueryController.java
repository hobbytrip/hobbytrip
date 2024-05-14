package capstone.chatservice.domain.server.controller;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
import capstone.chatservice.domain.server.dto.request.ServerMessageTypingRequest;
import capstone.chatservice.domain.server.service.query.ServerMessageQueryService;
import capstone.chatservice.global.common.dto.DataResponseDto;
import capstone.chatservice.global.common.dto.PageResponseDto;
import capstone.chatservice.infra.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ServerMessageQueryController {

    private final KafkaProducer producerService;
    private final ServerMessageQueryService queryService;

    @GetMapping("/api/chat/server/messages/channel")
    public DataResponseDto<Object> getMessages(@RequestParam(value = "channelId") Long channelId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size) {

        Page<ServerMessageDto> messages = queryService.getMessages(channelId, page, size);
        PageResponseDto pageResponseDto = PageResponseDto.of(messages);
        return DataResponseDto.of(pageResponseDto);
    }

    @GetMapping("/api/chat/server/comments/message")
    public DataResponseDto<Object> getComments(@RequestParam(value = "parentId") Long parentId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size) {

        Page<ServerMessageDto> comments = queryService.getComments(parentId, page, size);
        PageResponseDto pageResponseDto = PageResponseDto.of(comments);
        return DataResponseDto.of(pageResponseDto);
    }

    @MessageMapping("/server/message/typing")
    public void typing(ServerMessageTypingRequest typingRequest) {
        ServerMessageDto serverMessageDto = ServerMessageDto.from(typingRequest);
        producerService.sendToServerChatTopic(serverMessageDto);
    }
}
