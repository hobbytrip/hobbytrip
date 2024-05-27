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
    private String dmChatTopic;

    @Value("${spring.kafka.topic.server-chat}")
    private String serverChatTopic;

    private final KafkaTemplate<String, DmNotificationDto> dmNotificationKafkaTemplate;
    private final KafkaTemplate<String, ServerNotificationDto> serverNotificationKafkaTemplate;


    public void sendToServerChatTopic(ServerNotificationDto notificationDto) {
        serverNotificationKafkaTemplate.send(serverChatTopic, notificationDto);
    }
    public void sendToDmChatTopic(DmNotificationDto notificationDto) {
        dmNotificationKafkaTemplate.send(dmChatTopic, notificationDto);
    }
}
