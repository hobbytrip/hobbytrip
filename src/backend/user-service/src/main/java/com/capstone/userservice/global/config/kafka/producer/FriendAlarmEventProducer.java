package com.capstone.userservice.global.config.kafka.producer;

import com.capstone.userservice.global.config.kafka.dto.FriendAlarmEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FriendAlarmEventProducer {

    @Value("${spring.kafka.topic.friend-alarm}")
    private String friendAlarmEventTopic;

    private final KafkaTemplate<String, FriendAlarmEventDto> FriendAlarmEventKafkaTemplate;

    public void sendToFriendAlarmEventTopic(FriendAlarmEventDto friendAlarmEventDto) {
        FriendAlarmEventKafkaTemplate.send(friendAlarmEventTopic, friendAlarmEventDto);
    }
}
