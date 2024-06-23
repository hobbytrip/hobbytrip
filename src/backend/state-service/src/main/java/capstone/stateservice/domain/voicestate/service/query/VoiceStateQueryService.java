package capstone.stateservice.domain.voicestate.service.query;

import java.util.Map;
import java.util.Set;

public interface VoiceStateQueryService {

    Map<Long, Set<Long>> getVoiceChannelUsersState(String serverId);
}
