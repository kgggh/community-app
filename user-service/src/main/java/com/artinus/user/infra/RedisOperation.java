package com.artinus.user.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisOperation {
    private final RedisTemplate<String, String> redisTemplate;

    public void add(String key, String value, long duration, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, duration, timeUnit);
    }
}
