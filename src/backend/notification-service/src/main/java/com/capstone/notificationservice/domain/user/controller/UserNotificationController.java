package com.capstone.notificationservice.domain.user.controller;


import com.capstone.notificationservice.domain.user.service.UserNotificationService;
import com.capstone.notificationservice.global.common.dto.DataResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class UserNotificationController {

    private final UserNotificationService notificationService;

    /**
     * @title 로그인 한 유저 sse 연결
     * Last-Event-ID: 클라이언트가 마지막으로 수시한 데이터의 ID값 (항상 들어있지 않음)
     * produces를 text/event-stream으로 해야만 SSE통신 가능
     */
    @GetMapping(value = "/friend-alarm/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestParam Long userId,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(userId, lastEventId);
    }
    @DeleteMapping("/friend-alarm")
    public DataResponseDto<Boolean> deleteServerNotifications(@RequestParam Long userId) {
        return DataResponseDto.of(notificationService.deleteNotifications(userId));
    }
}

