package capstone.stateservice.domain.userstate.service.command;

import capstone.stateservice.global.common.dto.DataResponseDto;
import capstone.stateservice.infra.kafka.consumer.state.dto.ConnectionStateInfo;

public interface UserStateCommandService {

    DataResponseDto<Long> saveUserConnectionState(ConnectionStateInfo connectionStateInfo);
}
