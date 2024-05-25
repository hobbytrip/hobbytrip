package capstone.chatservice.infra.kafka.producer.state;

import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionStateEventDto;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionStateInfo;
import capstone.chatservice.infra.kafka.producer.state.dto.UserLocationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StateEventProducer {

    @Value("${spring.kafka.topic.connection-state-event}")
    private String connectionStateEventTopic;

    @Value("${spring.kafka.topic.connection-state-info}")
    private String connectionStateInfoTopic;

    @Value("${spring.kafka.topic.user-location-event}")
    private String userLocationEventTopic;

    private final KafkaTemplate<String, ConnectionStateEventDto> connectionStateEventKafkaTemplate;
    private final KafkaTemplate<String, ConnectionStateInfo> connectionStateInfoKafkaTemplate;
    private final KafkaTemplate<String, UserLocationEventDto> userLocationEventKafkaTemplate;

    public void sendToConnectionStateEventTopic(ConnectionStateEventDto connectionStateEventDto) {
        connectionStateEventKafkaTemplate.send(connectionStateEventTopic, connectionStateEventDto);
    }

    public void sendToConnectionStateInfoTopic(ConnectionStateInfo connectionStateInfo) {
        connectionStateInfoKafkaTemplate.send(connectionStateInfoTopic, connectionStateInfo);
    }

    public void sendToUserLocationEventTopic(UserLocationEventDto userLocationEventDto) {
        userLocationEventKafkaTemplate.send(userLocationEventTopic, userLocationEventDto);
    }
}
