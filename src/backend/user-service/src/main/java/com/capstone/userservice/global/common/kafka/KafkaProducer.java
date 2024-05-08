package com.capstone.userservice.global.common.kafka;


import com.capstone.userservice.global.common.dto.kafka.UserStatusEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    @Value("${spring.kafka.topic.user-status}")
    private String userStatusTopic;

    private final KafkaTemplate<String, UserStatusEventDto> userStatusTemplate;

    public void sendToUserStatusTopic(UserStatusEventDto userStatusEventDto) {
        userStatusTemplate.send(userStatusTopic, userStatusEventDto);
    }
}
