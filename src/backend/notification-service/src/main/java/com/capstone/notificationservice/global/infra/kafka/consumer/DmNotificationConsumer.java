package com.capstone.notificationservice.global.infra.kafka.consumer;


import com.capstone.notificationservice.domain.dm.dto.DmNotificationDto;
import com.capstone.notificationservice.global.common.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DmNotificationConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.dm-chat}", groupId = "${spring.kafka.consumer.group-id.dm-notification}", containerFactory = "dmNotificationListenerContainerFactory")
    public void dmNotificationListener(DmNotificationDto notificationDto) {
        Long dmRoomId = notificationDto.getDmRoomId();
        messagingTemplate.convertAndSend("/topic/chat/" + dmRoomId, DataResponseDto.of(notificationDto));

    }
}
