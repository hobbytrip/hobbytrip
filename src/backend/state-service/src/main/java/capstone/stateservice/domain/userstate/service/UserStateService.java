package capstone.stateservice.domain.userstate.service;

import capstone.stateservice.global.common.dto.DataResponseDto;
import capstone.stateservice.infra.kafka.consumer.state.dto.ConnectionStateInfo;

public interface UserStateService {

    DataResponseDto<Long> saveUserConnectionState(ConnectionStateInfo connectionStateInfo);
}
