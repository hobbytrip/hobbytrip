package com.capstone.userservice.global.common.kafka;


import com.capstone.userservice.global.common.dto.kafka.FriendStatusEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    @Value("${spring.kafka.topic.user-status}")
    private String userStatusTopic;

    private final KafkaTemplate<String, FriendStatusEventDto> userStatusTemplate;

    public void sendToUserStatusTopic(FriendStatusEventDto friendStatusEventDto) {
        userStatusTemplate.send(userStatusTopic, friendStatusEventDto);
    }
}
