package com.capstone.notificationservice.domain.dm.repository;

import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    void saveEventCache(String emitterId, Object event);
    Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId);

    void deleteById(String id);
    void deleteAllEmitterStartWithId(String userId);
    void deleteAllEventCacheStartWithId(String userId);
}
