package capstone.stateservice.domain.location.service.query.impl;

import capstone.stateservice.domain.location.dto.UserLocationDto;
import capstone.stateservice.domain.location.service.query.LocationStateQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationStateQueryServiceImpl implements LocationStateQueryService {

    private static final String prefix = "LOCATION:STATE";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserLocationDto getUserLocationState(Long serverId, Long userId) {
        HashOperations<String, Long, Long> hashOperations = redisTemplate.opsForHash();
        String hashKey = prefix + userId;
        Long userLocation = hashOperations.get(hashKey, serverId);
        return new UserLocationDto(userLocation);
    }
}
