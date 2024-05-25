package capstone.stateservice.domain.voicestate.service.command.impl;

import capstone.stateservice.domain.voicestate.service.command.VoiceStateCommandService;
import capstone.stateservice.infra.kafka.consumer.state.dto.VoiceChannelEventDto;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoiceStateCommandServiceImpl implements VoiceStateCommandService {

    private static final String prefix = "VOICE:STATE";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveVoiceState(VoiceChannelEventDto voiceChannelEventDto) {
        HashOperations<String, String, Set<String>> hashOperations = redisTemplate.opsForHash();
        String hashKey = prefix + voiceChannelEventDto.getServerId();
        String userId = String.valueOf(voiceChannelEventDto.getUserId());
        String channelId = String.valueOf(voiceChannelEventDto.getChannelId());
        Set<String> userIds = hashOperations.get(hashKey, channelId);
        switch (voiceChannelEventDto.getVoiceConnectionState()) {
            case VOICE_JOIN -> handleVoiceChannelJoin(userId, channelId, userIds, hashKey, hashOperations);
            case VOICE_LEAVE -> handleVoiceChannelLeave(userId, channelId, userIds, hashKey, hashOperations);
        }
    }

    private void handleVoiceChannelJoin(String userId, String channelId, Set<String> userIds, String hashKey,
                                        HashOperations<String, String, Set<String>> hashOperations) {
        if (userIds != null) {
            userIds.add(userId);
        } else {
            userIds = new HashSet<>();
            userIds.add(userId);
        }
        hashOperations.put(hashKey, channelId, userIds);
    }

    private void handleVoiceChannelLeave(String userId, String channelId, Set<String> userIds, String hashKey,
                                         HashOperations<String, String, Set<String>> hashOperations) {
        if (userIds != null) {
            userIds.remove(userId);
            hashOperations.put(hashKey, channelId, userIds);
        }
    }
}
