package capstone.stateservice.infra.kafka.consumer.state;

import capstone.stateservice.domain.userstate.service.command.UserStateCommandService;
import capstone.stateservice.infra.kafka.consumer.state.dto.ConnectionStateInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConnectionStateInfoConsumer {

    private final UserStateCommandService userStateCommandService;

    @KafkaListener(topics = "${spring.kafka.topic.connection-state-info}", groupId = "${spring.kafka.consumer.group-id.connection-state-info}", containerFactory = "connectionStateInfoListenerContainerFactory")
    public void connectionStateInfoListener(ConnectionStateInfo connectionStateInfo) {
        userStateCommandService.saveUserConnectionState(connectionStateInfo);
    }
}
