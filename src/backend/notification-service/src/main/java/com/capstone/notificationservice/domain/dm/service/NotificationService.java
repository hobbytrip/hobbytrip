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
    private final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public SseEmitter subscribe(String userId, String lastEventId) {
        String emitterCreatedTimeByUserId = makeTimeIncludeUserId(userId);
        SseEmitter emitter = emitterRepository.save(userId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterCreatedTimeByUserId));
        emitter.onTimeout(()-> emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterCreatedTimeByUserId));

        //503 에러 방지를 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeUserId(userId);
        sendNotification(emitter, userId, emitterCreatedTimeByUserId,
                "EventStream Created. [memberName=" + userId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if(hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterCreatedTimeByUserId, emitter);

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
