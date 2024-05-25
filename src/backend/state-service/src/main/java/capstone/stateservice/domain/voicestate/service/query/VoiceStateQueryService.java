package capstone.stateservice.domain.voicestate.service.query;

import java.util.Map;
import java.util.Set;

public interface VoiceStateQueryService {

    Map<String, Set<String>> getVoiceChannelUsersState(String serverId);
}
