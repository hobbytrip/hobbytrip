package capstone.communityservice.global.external.dto;

import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
public class ServerUsersStateResponse {
    Map<String, Set<String>> voiceChannelUsersState;
    Map<Long, ConnectionState> usersConnectionState;
}
