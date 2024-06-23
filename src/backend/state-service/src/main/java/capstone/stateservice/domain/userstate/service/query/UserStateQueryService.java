package capstone.stateservice.domain.userstate.service.query;

import capstone.stateservice.domain.model.ConnectionState;
import java.util.List;
import java.util.Map;

public interface UserStateQueryService {

    Map<Long, ConnectionState> getUsersConnectionState(List<Long> userIds);
}
