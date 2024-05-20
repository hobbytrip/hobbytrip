package com.capstone.notificationservice.global.config.kafka;

import com.capstone.notificationservice.domain.dm.dto.DmNotificationDto;
import com.capstone.notificationservice.domain.server.dto.ServerNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topic.direct-chat}")
    private String dmNotificationTopic;

    @Value("${spring.kafka.topic.server-chat}")
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
