package capstone.stateservice.domain.voicestate.service.query.impl;

import capstone.stateservice.domain.voicestate.service.query.VoiceStateQueryService;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoiceStateQueryServiceImpl implements VoiceStateQueryService {

    private static final String prefix = "VOICE:STATE";
    private final RedisTemplate<String, Object> redisTemplate;

    public Map<Long, Set<Long>> getVoiceChannelUsersState(String serverId) {
        HashOperations<String, Long, Set<Long>> hashOperations = redisTemplate.opsForHash();
        String hashKey = prefix + serverId;
        return hashOperations.entries(hashKey);
    }
}
