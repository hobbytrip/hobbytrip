package capstone.stateservice.domain.userstate.dto.response;

import capstone.stateservice.domain.model.ConnectionState;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerUserStateResponse {

    Map<String, Set<String>> voiceChannelUsersState;
    Map<Long, ConnectionState> usersConnectionState;
}
