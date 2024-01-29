package com.example.cache.service;

import com.example.cache.domain.entity.RedisHashUser;
import com.example.cache.domain.entity.User;
import com.example.cache.domain.repository.RedisHashUserRepository;
import com.example.cache.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.example.cache.config.CacheConfig.CACHE1;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisHashUserRepository redisHashUserRepository;
    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    public User getUser(final Long id){
        var key = "user:%d".formatted(id);
        // 1. cache get
        var cachedUser = objectRedisTemplate.opsForValue().get(key);
        if(cachedUser != null){
            return (User) cachedUser;
        }
        User user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));

        // 2. else db -> cache set
        return user;
    }

    public RedisHashUser getUser2(final Long id){

        //redis 값이 있으면 리턴
        var cachedUser = redisHashUserRepository.findById(id).orElseGet(() ->{
            User user = userRepository.findById(id).orElseThrow();
            return redisHashUserRepository.save(RedisHashUser.builder()
                        .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .build());
        });

        return cachedUser;
    }

    @Cacheable(cacheNames = CACHE1, key = "'user:' + #id") //전달받은 id값 앞에 키에 추가로 user:가 붙음
    public User getUser3(final Long id){
        return userRepository.findById(id).orElseThrow();
    }
    // vegeta라는 대용량트래픽 테스트 툴 사용하여 성능 비교
}
