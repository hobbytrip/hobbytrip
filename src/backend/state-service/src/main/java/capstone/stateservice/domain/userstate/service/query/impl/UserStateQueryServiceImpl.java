package capstone.stateservice.domain.userstate.service.query.impl;

import capstone.stateservice.domain.model.ConnectionState;
import capstone.stateservice.domain.userstate.repository.UserStateRepository;
import capstone.stateservice.domain.userstate.service.query.UserStateQueryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStateQueryServiceImpl implements UserStateQueryService {

    private final UserStateRepository userStateRepository;

    @Override
    public Map<Long, ConnectionState> getUsersConnectionState(List<Long> userIds) {
        Map<Long, ConnectionState> usersConnectionState = new HashMap<>();
        for (Long userId : userIds) {
            userStateRepository.findById(String.valueOf(userId))
                    .ifPresent(userState -> usersConnectionState.put(userId, userState.getConnectionState()));
        }
        return usersConnectionState;
    }
}
