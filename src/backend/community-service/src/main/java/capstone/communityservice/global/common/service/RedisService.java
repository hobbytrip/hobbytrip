package capstone.communityservice.global.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.time.ZoneOffset.UTC;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setValues(String key, String value){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value);
    }

    public void setValues(String key, String value, Duration duration){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value, duration);
    }

    @Transactional(readOnly = true)
    public String getValues(String key){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return (values.get(key) == null) ? "false" : (String) values.get(key);
    }

    public void deleteValues(String key){
        redisTemplate.delete(key);
    }

    public void expireValues(String key, int timeout){
        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    public void setHashOps(String key, Map<String, String> data){
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        values.putAll(key, data);
    }

    @Transactional(readOnly = true)
    public String getHashOps(String key, String hashKey){
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        return Boolean.TRUE.equals(values.hasKey(key, hashKey)) ? (String) redisTemplate.opsForHash().get(key, hashKey) : "";
    }

    public void deleteHashOps(String key, String hashKey){
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        values.delete(key, hashKey);
    }

    public boolean checkExistsValue(String value){
        return !value.equals("false");
    }

    public static Duration toTomorrow() {
        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        final LocalDateTime tomorrow = now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return Duration.between(now, tomorrow);
    }
}
