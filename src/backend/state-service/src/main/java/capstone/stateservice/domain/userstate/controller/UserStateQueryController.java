package capstone.stateservice.domain.userstate.controller;

import capstone.stateservice.domain.model.ConnectionState;
import capstone.stateservice.domain.userstate.dto.response.ServerUserStateResponse;
import capstone.stateservice.domain.userstate.dto.response.UserConnectionStateResponse;
import capstone.stateservice.domain.userstate.service.query.UserStateQueryService;
import capstone.stateservice.domain.voicestate.service.query.VoiceStateQueryService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserStateQueryController {

    private final UserStateQueryService userStateQueryService;
    private final VoiceStateQueryService voiceStateQueryService;

    @GetMapping("/feign/server/user/state")
    public ServerUserStateResponse getServerUsersState(
            @RequestParam Long serverId,
            @RequestParam List<Long> userIds) {

        Map<Long, Set<Long>> voiceChannelUsersState = voiceStateQueryService.getVoiceChannelUsersState(
                String.valueOf(serverId));
        Map<Long, ConnectionState> usersConnectionState = userStateQueryService.getUsersConnectionState(userIds);

        return new ServerUserStateResponse(voiceChannelUsersState, usersConnectionState);
    }

    @GetMapping("/feign/user/connection/state")
    public UserConnectionStateResponse getUsersConnectionState(
            @RequestParam List<Long> userIds) {

        Map<Long, ConnectionState> usersConnectionState = userStateQueryService.getUsersConnectionState(userIds);
        return new UserConnectionStateResponse(usersConnectionState);
    }
}
