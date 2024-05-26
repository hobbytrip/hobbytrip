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

    private Map<Long, Set<Long>> voiceChannelUsersState;
    private Map<Long, ConnectionState> usersConnectionState;
}
