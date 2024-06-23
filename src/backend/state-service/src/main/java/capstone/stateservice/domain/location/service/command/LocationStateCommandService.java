package capstone.stateservice.domain.location.service.command;

import capstone.stateservice.infra.kafka.consumer.location.dto.UserLocationEventDto;

public interface LocationStateCommandService {

    void saveLocationState(UserLocationEventDto userLocationEventDto);
}
