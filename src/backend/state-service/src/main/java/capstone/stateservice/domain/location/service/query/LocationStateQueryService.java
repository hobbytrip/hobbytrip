package capstone.stateservice.domain.location.service.query;

import capstone.stateservice.domain.location.dto.UserLocationDto;

public interface LocationStateQueryService {

    UserLocationDto getUserLocationState(Long serverId, Long userId);
}
