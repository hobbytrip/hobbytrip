package com.capstone.notificationservice.domain.user.service;

import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.dm.exception.DmException;
import com.capstone.notificationservice.domain.dm.repository.EmitterRepository;
import com.capstone.notificationservice.domain.server.dto.MentionType;
import com.capstone.notificationservice.domain.server.entity.ServerNotification;
import com.capstone.notificationservice.domain.user.dto.UserNotificationDto;
import com.capstone.notificationservice.domain.user.entity.User;
import com.capstone.notificationservice.domain.user.entity.UserNotification;
import com.capstone.notificationservice.domain.user.exception.UserException;
import com.capstone.notificationservice.domain.user.repository.UserNotificationRepository;
import com.capstone.notificationservice.global.exception.Code;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserNotificationService {
    private final EmitterRepository emitterRepository;
    private final UserService userService;
    private final UserNotificationRepository userNotificationRepository;
    private final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public SseEmitter subscribe(Long userId, String lastEventId) {
        //emitterUserID Create
        String emitterId = makeTimeIncludeUserId(userId);

        //SseEmitter 객체 만들고 반환, id 를 key 로 SseEmitter value 로 저장
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        //503 에러 방지를 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeUserId(userId);
        sendNotification(emitter, emitterId, eventId,
                "EventStream Created. [memberName=" + userId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterId, emitter);

        }
        return emitter;
    }


    private String makeTimeIncludeUserId(Long userId) {
        return userId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
            throw new DmException(Code.INTERNAL_ERROR, "연결 오류 !");
        }
    }

    private Boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter) {
        Map<String, SseEmitter> eventCaches = emitterRepository.findAllEmitterStartWithByUserId(String.valueOf(userId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }


    public void send(Long userId, String receiverEmail) {
        UserNotification notification = createNotification(userId, receiverEmail);
        userNotificationRepository.save(notification);

        String eventCreatedTime = userId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(eventCreatedTime);
            emitters.forEach(
                    (key, emitter) -> {
                        emitterRepository.saveEventCache(key, userId);
                        sendNotification(emitter, eventCreatedTime, key,new UserNotificationDto(userId, receiverEmail));
                    }
            );
    }

    private UserNotification createNotification(Long userId, String receiverEmail) {
        return UserNotification.builder()
                .userId(userId)
                .receiver(userService.findUser(receiverEmail))
                .build();
    }


    @Transactional
    public Boolean deleteNotifications(Long userId) {
        try {
            userNotificationRepository.deleteById(userId);
            return true;
        } catch (RuntimeException e) {
            throw new UserException(Code.INTERNAL_ERROR, "알림 데이터 삭제에 실패했습니다.");
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic.friend-alarm}", groupId = "${spring.kafka.consumer.group-id.friend-alarm}", containerFactory = "FriendAlarmListenerContainerFactory")
    public void kafkaSend(UserNotificationDto userNotificationDto) {
        Long userId = userNotificationDto.getUserId();
        String receiverEmail = userNotificationDto.getReceiverEmail();

        log.info("getUserId {}", userNotificationDto.getUserId());
        log.info("receiverEmail {}", userNotificationDto.getReceiverEmail());
        send(userId, receiverEmail);
    }
}
