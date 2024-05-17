package capstone.chatservice.infra.kafka.consumer.state;

import capstone.chatservice.infra.kafka.consumer.state.dto.ConnectionStateEventResponse;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionStateEventDto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class connectionStateEventConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.connection-state-event}", groupId = "${spring.kafka.consumer.group-id.connection-state-event}", containerFactory = "connectionStateEventListenerContainerFactory")
    public void connectionStateEventListener(ConnectionStateEventDto connectionStateEventDto) {
        if (connectionStateEventDto != null && ("CONNECT".equals(connectionStateEventDto.getType())
                || "DISCONNECT".equals(connectionStateEventDto.getType()))) {

            ConnectionStateEventResponse connectionStateEventResponse = ConnectionStateEventResponse.from(
                    connectionStateEventDto);

            List<String> topicPaths = generateTopicPaths(connectionStateEventDto);
            topicPaths.parallelStream()
                    .forEach(topicPath -> messagingTemplate.convertAndSend(topicPath, connectionStateEventResponse));
        }
    }

    private List<String> generateTopicPaths(ConnectionStateEventDto connectionStateEventDto) {
        return Stream.concat(
                        connectionStateEventDto.getServerIds().stream().map(serverId -> "/topic/server/" + serverId),
                        connectionStateEventDto.getRoomIds().stream().map(roomId -> "/topic/direct/" + roomId))
                .collect(Collectors.toList());
    }
}
