package capstone.stateservice.domain.userstate.service.command.impl;

import capstone.stateservice.domain.userstate.domain.UserState;
import capstone.stateservice.domain.userstate.repository.UserStateRepository;
import capstone.stateservice.domain.userstate.service.command.UserStateCommandService;
import capstone.stateservice.global.common.dto.DataResponseDto;
import capstone.stateservice.infra.kafka.consumer.state.dto.ConnectionStateInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStateCommandServiceImpl implements UserStateCommandService {

    private final UserStateRepository userStateRepository;

    @Override
    public DataResponseDto<Long> saveUserConnectionState(ConnectionStateInfo connectionStateInfo) {
        Long userId = null;
        switch (connectionStateInfo.getType()) {
            case CONNECT -> handleConnectionState(connectionStateInfo);
            case DISCONNECT -> userId = handleDisconnectionState(connectionStateInfo);
        }

        return DataResponseDto.of(userId);
    }

    private void handleConnectionState(ConnectionStateInfo connectionStateInfo) {
        UserState userState = userStateRepository.findById(String.valueOf(connectionStateInfo.getUserId()))
                .orElse(UserState.builder()
                        .userId(connectionStateInfo.getUserId())
                        .build());

        userState.modify(connectionStateInfo.getSessionId(), connectionStateInfo.getState());
        userStateRepository.save(userState);
    }

    private Long handleDisconnectionState(ConnectionStateInfo connectionStateInfo) {
        return userStateRepository.findByChatSessionId(connectionStateInfo.getSessionId())
                .map(userState -> {
                    userState.modify(connectionStateInfo.getState());
                    userStateRepository.save(userState);
                    return userState.getUserId();
                })
                .orElse(null);
    }
}
