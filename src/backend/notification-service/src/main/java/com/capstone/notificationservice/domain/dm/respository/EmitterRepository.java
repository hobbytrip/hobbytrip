package com.capstone.notificationservice.domain.dm.respository;

import java.util.Map;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {
    SseEmitter save(String userId, SseEmitter sseEmitter);
    void saveEventCache(String receiverName, Object event);
    Map<String, SseEmitter> findAllEmitterStartWithByUserId(String memberId);
    Map<String, Object> findAllEventCacheStartWithByUserId(String memberId);
    void deleteByEmitterCreatedTimeWithMemberName(String id);
    void deleteById(String memberId);
    void deleteAllEventCacheStartWithId(String memberId);
}
