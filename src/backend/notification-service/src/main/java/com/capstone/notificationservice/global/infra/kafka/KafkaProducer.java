package com.capstone.notificationservice.global.infra.kafka;


import com.capstone.notificationservice.domain.dm.dto.DmNotificationDto;
import com.capstone.notificationservice.domain.server.dto.ServerNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topic.dm-notification}")
    private String dmNotificationTopic;

    @Value("${spring.kafka.topic.server-notification}")
    private String serverNotificationTopic;

    private final KafkaTemplate<String, DmNotificationDto> dmNotificationKafkaTemplate;
    private final KafkaTemplate<String, ServerNotificationDto> serverNotificationKafkaTemplate;

    public void sendToDmNotificationTopic(DmNotificationDto notificationDto) {
        dmNotificationKafkaTemplate.send(dmNotificationTopic, notificationDto);
    }

    public void sendToServerNotificationTopic(ServerNotificationDto notificationDto) {
        serverNotificationKafkaTemplate.send(serverNotificationTopic, notificationDto);
    }
}
