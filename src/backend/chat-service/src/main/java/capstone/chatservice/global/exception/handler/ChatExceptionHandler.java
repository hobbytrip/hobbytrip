package capstone.chatservice.global.exception.handler;

import capstone.chatservice.domain.dm.exception.DmChatException;
import capstone.chatservice.domain.forum.exception.ForumChatException;
import capstone.chatservice.domain.server.exception.ServerChatException;
import capstone.chatservice.global.common.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ChatExceptionHandler {

    @MessageExceptionHandler(DmChatException.class)
    @SendToUser(destinations = "/queue/errors", broadcast = false)
    protected ErrorResponseDto dmChatExceptionHandler(DmChatException e) {

        log.error("DmChatException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    @MessageExceptionHandler(ForumChatException.class)
    @SendToUser(destinations = "/queue/errors", broadcast = false)
    protected ErrorResponseDto forumChatExceptionHandler(ForumChatException e) {

        log.error("ForumChatException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    @MessageExceptionHandler(ServerChatException.class)
    @SendToUser(destinations = "/queue/errors", broadcast = false)
    protected ErrorResponseDto serverChatExceptionHandler(ServerChatException e) {

        log.error("ServerChatException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }
}