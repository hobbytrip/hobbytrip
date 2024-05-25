package com.capstone.notificationservice.domain.dm.controller;

import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.dm.dto.DmNotificationDto;
import com.capstone.notificationservice.domain.dm.repository.EmitterRepositoryImpl;
import com.capstone.notificationservice.domain.dm.service.DmNotificationService;
import com.capstone.notificationservice.global.common.dto.DataResponseDto;
import com.capstone.notificationservice.global.config.kafka.KafkaProducer;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final KafkaProducer kafkaProducer;
    private final DmNotificationService dmNotificationService;

    @PostMapping("/test/send")
    public ResponseEntity<String> sendTestEvent(@RequestParam Long userId,
                                                @RequestParam Long dmRoomId,
                                                @RequestParam String content,
                                                @RequestParam AlarmType alarmType) {
        DmNotificationDto notificationDto = new DmNotificationDto(dmRoomId,userId,Arrays.asList(userId),content,alarmType);
        notificationDto.setUserId(userId);
        notificationDto.setDmRoomId(dmRoomId);
        notificationDto.setAlarmType(alarmType);
        notificationDto.setContent(content);
        notificationDto.setReceiverIds(Arrays.asList(userId)); // 자기 자신에게 전송하는 예시

        kafkaProducer.sendToDmChatTopic(notificationDto);

        return ResponseEntity.ok("Event sent to Kafka");
    }

    @GetMapping(value = "/test/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestParam Long userId,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return dmNotificationService.subscribe(userId, lastEventId);
    }


}

