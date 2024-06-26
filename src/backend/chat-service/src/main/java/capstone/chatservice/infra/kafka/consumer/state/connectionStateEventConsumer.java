package capstone.chatservice.infra.kafka.consumer.state;

import capstone.chatservice.infra.kafka.consumer.state.dto.ConnectionStateEventResponse;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionStateEventDto;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionType;
import java.util.List;
import java.util.Optional;
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
        if (connectionStateEventDto != null && (connectionStateEventDto.getType() == ConnectionType.CONNECT
                || connectionStateEventDto.getType() == ConnectionType.DISCONNECT)) {

            ConnectionStateEventResponse connectionStateEventResponse = ConnectionStateEventResponse.from(
                    connectionStateEventDto);

            List<String> topicPaths = generateTopicPaths(connectionStateEventDto);
            topicPaths.parallelStream()
                    .forEach(topicPath -> messagingTemplate.convertAndSend(topicPath, connectionStateEventResponse));
        }
    }

    private List<String> generateTopicPaths(ConnectionStateEventDto connectionStateEventDto) {
        return Stream.concat(
                        Optional.ofNullable(connectionStateEventDto.getServerIds())
                                .stream()
                                .flatMap(List::stream)
                                .map(serverId -> "/topic/server/" + serverId),
                        Optional.ofNullable(connectionStateEventDto.getRoomIds())
                                .stream()
                                .flatMap(List::stream)
                                .map(roomId -> "/topic/direct/" + roomId))
                .collect(Collectors.toList());
    }
}
