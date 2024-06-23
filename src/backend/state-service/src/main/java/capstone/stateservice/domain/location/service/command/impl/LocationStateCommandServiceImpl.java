package capstone.stateservice.domain.location.service.command.impl;

import capstone.stateservice.domain.location.service.command.LocationStateCommandService;
import capstone.stateservice.infra.kafka.consumer.location.dto.UserLocationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationStateCommandServiceImpl implements LocationStateCommandService {

    private static final String prefix = "LOCATION:STATE";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveLocationState(UserLocationEventDto userLocationEventDto) {
        HashOperations<String, Long, Long> hashOperations = redisTemplate.opsForHash();
        String hashKey = prefix + userLocationEventDto.getUserId();
        Long serverId = userLocationEventDto.getServerId();
        Long channelId = userLocationEventDto.getChannelId();
        hashOperations.put(hashKey, serverId, channelId);
        log.info("################ 유저 채널 위치 저장 ################");
        log.info("userId {}", userLocationEventDto.getUserId());
        log.info("serverId {}", serverId);
        log.info("channelId {}", channelId);
        log.info("################");
    }
}