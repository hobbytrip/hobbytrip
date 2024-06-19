package com.capstone.notificationservice.domain.server.service;

import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.dm.repository.EmitterRepository;
import com.capstone.notificationservice.domain.server.dto.MentionType;
import com.capstone.notificationservice.domain.server.dto.ServerNotificationDto;
import com.capstone.notificationservice.domain.server.dto.response.ServerNotificationResponse;
import com.capstone.notificationservice.domain.server.entity.ServerNotification;
import com.capstone.notificationservice.domain.server.exception.ServerException;
import com.capstone.notificationservice.domain.server.repository.ServerNotificationRepository;
import com.capstone.notificationservice.domain.user.entity.User;
import com.capstone.notificationservice.domain.user.service.UserService;
import com.capstone.notificationservice.global.exception.Code;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmitterServerNotificationService {
    private final EmitterRepository emitterRepository;
    private final ServerNotificationRepository serverNotificationRepository;
    private final UserService userService;
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
            throw new ServerException(Code.INTERNAL_ERROR, "연결 오류 !");
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


    public void send(Long userId, Long serverId, String writer, String content, String profileImage,
                     List<Long> receiverIds, AlarmType alarmType,  MentionType mentionType) {
        List<User> receivers = receiverIds.stream()
                .map(userService::findUser)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        List<ServerNotification> serverNotifications = serverNotificationRepository.saveAll(
                createNotification(userId, serverId, writer, content, profileImage, receivers, alarmType, mentionType));


        serverNotifications.forEach(serverNotification -> {
            String receiverId = userId + "_" + System.currentTimeMillis();
            String eventId = receiverId + "_" + System.currentTimeMillis();
            Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverId);
            emitters.forEach(
                    (key, emitter) -> {
                        emitterRepository.saveEventCache(key, serverNotification);
                        sendNotification(emitter, eventId, key,
                                ServerNotificationResponse.from(serverNotification, userId));
                    }
            );
        });
    }

    private List<ServerNotification> createNotification(Long userId, Long serverId, String writer, String content,
                                                        String profileImage, List<User> receivers, AlarmType alarmType,
                                                        MentionType mentionType
                                                        ) {
        return receivers.stream()
                .map(receiver -> ServerNotification.builder()
                        .receiver(receiver)
                        .serverId(serverId)
                        .alarmType(alarmType)
                        .mentionType(mentionType)
                        .content(content)
                        .isRead(false)
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public Boolean deleteNotifications(Long userId, Long serverId) {
        try {
            serverNotificationRepository.deleteByUserIdAndServerId(userId, serverId);
            return true;
        } catch (RuntimeException e) {
            throw new ServerException(Code.INTERNAL_ERROR, "알림 데이터 삭제에 실패했습니다.");
        }
    }

    @Transactional
    public List<Long> getDistinctServerRoomId(Long userId) {
        return serverNotificationRepository.findDistinctServerIds(userId);
    }


    @KafkaListener(topics = "${spring.kafka.topic.server-notification}", groupId = "${spring.kafka.consumer.group-id.server-notification}", containerFactory="serverNotificationListenerContainerFactory")
    public void kafkaSend(ServerNotificationDto serverNotificationDto) throws JsonProcessingException {
//        ServerNotificationDto serverNotification = new ObjectMapper().readValue(record.value().toString(),
//                ServerNotificationDto.class);

        Long sendId = serverNotificationDto.getUserId(); // Long 타입으로 변경
        Long serverId = serverNotificationDto.getServerId();// DM 방 ID, DmNotificationDto에 해당 필드가 존재한다고 가정
        AlarmType alarmType = serverNotificationDto.getAlarmType();
        MentionType mentionType = serverNotificationDto.getMentionType();
        String content = serverNotificationDto.getContent();
        List<Long> receiverIds = serverNotificationDto.getReceiverIds(); // 수신자 ID 목록, DmNotificationDto에 해당 필드가 존재한다고 가정
        String writer=serverNotificationDto.getWriter();
        String profileImage=serverNotificationDto.getProfileImage();

        log.info("getUserId {}", serverNotificationDto.getUserId());
        log.info("getServerId {}", serverNotificationDto.getServerId());
        log.info("getContent {}", serverNotificationDto.getContent());

        send(sendId, serverId, writer, content, profileImage, receiverIds, alarmType,mentionType);

    }
}
