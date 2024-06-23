package com.capstone.notificationservice.domain.dm.service;


import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.dm.dto.DmNotificationDto;
import com.capstone.notificationservice.domain.dm.dto.response.DmNotificationResponse;
import com.capstone.notificationservice.domain.dm.entity.DmNotification;
import com.capstone.notificationservice.domain.dm.exception.DmException;
import com.capstone.notificationservice.domain.dm.repository.EmitterRepository;
import com.capstone.notificationservice.domain.dm.repository.EmitterRepositoryImpl;
import com.capstone.notificationservice.domain.dm.repository.NotificationRepository;
import com.capstone.notificationservice.domain.server.exception.ServerException;
import com.capstone.notificationservice.domain.user.entity.User;
import com.capstone.notificationservice.domain.user.entity.UserNotification;
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
@RequiredArgsConstructor
@Slf4j
public class DmNotificationService {
    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
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
            throw new DmException(Code.INTERNAL_ERROR, "연결 오류 !");
        }
    }
    private void sendNotificationDto(SseEmitter emitter, String eventId, String emitterId, DmNotificationDto data) {
        StringBuilder users = new StringBuilder();

        for(Long userId :data.getReceiverIds() ){
            users.append(userId);
            users.append(",");
        }
        String userIds=users.substring(0,users.length()-1);
        log.info("3 userId {}", String.valueOf(data.getUserId()));

        try {
            log.info("4 userId {}", String.valueOf(data.getUserId()));
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("userId")
                    .data(String.valueOf(data.getUserId())));
//                    .name("dmRoomId")
//                    .data(String.valueOf(data.getDmRoomId()))
//                    .name("content")
//                    .data(String.valueOf(data.getContent()))
//                    .name("writer")
//                    .data(String.valueOf(data.getProfileImage()))
//                    .name("alarmType")
//                    .data(String.valueOf(data.getAlarmType()))
//                    .name("receiverIds")
//                    .data(userIds));
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


    public void send(Long userId, Long dmRoomId, String content, String writer, String profileImage,
                     AlarmType alarmType,  List<Long> receiverIds) {
        List<User> receivers = receiverIds.stream()
                .map(userService::findUser)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        List<DmNotification> dmNotifications = notificationRepository.saveAll(
                createNotification(userId, dmRoomId, content,writer,profileImage, alarmType, receivers));

        dmNotifications.forEach(dmNotification -> {
            String receiverId = userId + "_" + System.currentTimeMillis();
            String eventId = receiverId + "_" + System.currentTimeMillis();
            Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverId);
            emitters.forEach(
                    (key, emitter) -> {
                        emitterRepository.saveEventCache(key, dmNotification);
                        sendNotification(emitter, eventId, key, DmNotificationResponse.from(dmNotification, userId));
                    }
            );
        });
    }
    public void sendDto(Long userId, Long dmRoomId, String content, String writer, String profileImage,
                        AlarmType alarmType,  List<Long> receiverIds) {
        List<User> receivers = receiverIds.stream()
                .map(userService::findUser)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        List<DmNotification> dmNotifications =
                createNotification(userId, dmRoomId, content,writer,profileImage, alarmType, receivers);

        dmNotifications.forEach(dmNotification -> {
            String receiverId = userId + "_" + System.currentTimeMillis();
            String eventId = receiverId + "_" + System.currentTimeMillis();
            Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverId);
            log.info("1 userId {}", userId);
            emitters.forEach(
                    (key, emitter) -> {
                        emitterRepository.saveEventCache(key, dmNotification);
                        log.info("2 userId {}", userId);
                        sendNotificationDto(emitter, eventId, key, DmNotificationResponse.from(dmNotification, userId));
                    }
            );
        });
    }

    private List<DmNotification> createNotification(Long userId, Long dmRoomId, String content, String writer, String profileImage,
                                                    AlarmType alarmType, List<User> receivers) {
        return receivers.stream()
                .map(receiver -> DmNotification.builder()
                        .receiver(receiver)
                        .dmRoomId(dmRoomId)
                        .alarmType(alarmType)
                        .content(content)
                        .isRead(false)
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional
    public Boolean deleteNotifications(Long userId, Long dmRoomId) {
        try {
            notificationRepository.deleteByUserIdAndDmRoomId(userId, dmRoomId);
            return true;
        } catch (RuntimeException e) {
            throw new ServerException(Code.INTERNAL_ERROR, "알림 데이터 삭제에 실패했습니다.");
        }
    }

    @Transactional
    public List<Long> getDistinctDmRoomId(Long userId) {
        return notificationRepository.findDistinctDmRoomIds(userId);
    }

    @KafkaListener(topics = "${spring.kafka.topic.dm-notification}", groupId = "${spring.kafka.consumer.group-id.dm-notification}", containerFactory = "dmNotificationListenerContainerFactory")
    public void kafkaSend(DmNotificationDto dmNotificationDto) throws JsonProcessingException {
//        DmNotificationDto dmNotification = new ObjectMapper().readValue(record.value().toString(),
//                DmNotificationDto.class);

        Long sendId = dmNotificationDto.getUserId(); // Long 타입으로 변경
        Long dmRoomId = dmNotificationDto.getDmRoomId(); // DM 방 ID, DmNotificationDto에 해당 필드가 존재한다고 가정
        AlarmType alarmType = dmNotificationDto.getAlarmType(); // String을 AlarmType으로 변환
        String content = dmNotificationDto.getContent();
        List<Long> receiverIds = dmNotificationDto.getReceiverIds(); // 수신자 ID 목록, DmNotificationDto에 해당 필드가 존재한다고 가정
        String writer=dmNotificationDto.getWriter();
        String profileImage=dmNotificationDto.getProfileImage();

        log.info("dmAlarmConsumer");
        log.info("getUserId {}", dmNotificationDto.getUserId());
        log.info("getDmRoomId {}", dmNotificationDto.getDmRoomId());
        log.info("getAlarmType {}", dmNotificationDto.getAlarmType());
        log.info("getContent {}", dmNotificationDto.getContent());
        log.info("getReceiverIds {}", dmNotificationDto.getReceiverIds());

        sendDto(sendId, dmRoomId, content, writer, profileImage, alarmType, receiverIds);

    }
}
