package com.capstone.notificationservice.domain.dm.service;


import com.capstone.notificationservice.domain.dm.respository.EmitterRepository;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public SseEmitter subscribe(String userId, String lastEventId) {
        //emitterUserID Create
        String emitterUserId = makeTimeIncludeUserId(userId);

        //SseEmitter 객체 만들고 반환, id 를 key 로 SseEmitter value 로 저장
        SseEmitter emitter = emitterRepository.save(userId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterUserId));
        emitter.onTimeout(()-> emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterUserId));

        //503 에러 방지를 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeUserId(userId);
        sendNotification(emitter, userId, emitterUserId,
                "EventStream Created. [memberName=" + userId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if(hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterUserId, emitter);

        }

        return emitter;
    }

    private String makeTimeIncludeUserId(String userId) {
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
        }
    }

    private Boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, String userId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(userId);
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

}
