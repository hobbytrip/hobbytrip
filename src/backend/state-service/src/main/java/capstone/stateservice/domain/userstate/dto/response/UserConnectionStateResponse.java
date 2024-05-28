package capstone.stateservice.domain.userstate.dto.response;

import capstone.stateservice.domain.model.ConnectionState;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConnectionStateResponse {

    private Map<Long, ConnectionState> usersConnectionState;
}
