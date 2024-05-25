package com.capstone.notificationservice.domain.server;

import com.capstone.notificationservice.domain.server.service.EmitterServerNotificationService;
import com.capstone.notificationservice.global.common.dto.DataResponseDto;
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
public class ServerNotificationController {

    private final EmitterServerNotificationService notificationService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestParam Long userId,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(userId, lastEventId);
    }


    @DeleteMapping("/server/{serverId}")
    public DataResponseDto<Boolean> deleteServerNotifications(@PathVariable Long serverId, Long userId) {
        Boolean result = notificationService.deleteNotifications(userId, serverId);
        return DataResponseDto.of(result);
    }

}
