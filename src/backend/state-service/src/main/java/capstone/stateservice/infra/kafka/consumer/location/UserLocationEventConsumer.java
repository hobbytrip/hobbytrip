package capstone.stateservice.infra.kafka.consumer.location;

import capstone.stateservice.domain.location.service.command.LocationStateCommandService;
import capstone.stateservice.infra.kafka.consumer.location.dto.UserLocationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserLocationEventConsumer {

    private final LocationStateCommandService locationStateCommandService;

    @KafkaListener(topics = "${spring.kafka.topic.user-location-event}", groupId = "${spring.kafka.consumer.group-id.user-location-event}", containerFactory = "userLocationEventListenerContainerFactory")
    public void userLocationEventListener(UserLocationEventDto userLocationEventDto) {
        locationStateCommandService.saveLocationState(userLocationEventDto);
    }
}
