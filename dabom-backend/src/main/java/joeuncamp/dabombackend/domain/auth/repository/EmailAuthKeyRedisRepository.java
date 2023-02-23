package joeuncamp.dabombackend.domain.auth.repository;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.global.constant.JwtExpiration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmailAuthKeyRedisRepository {
    private final StringRedisTemplate redisTemplate;
    private static final long EXPIRATION = 30;
    private final static String PREFIX_AUTH = "EMAIL-AUTH:";

    /**
     * 이메일 - 인증키 쌍을 저장합니다.
     *
     * @param email   이메일
     * @param authKey 인증키
     */
    public void saveAuthKey(String email, String authKey) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_AUTH + email;
        valueOperations.set(key, authKey);
        redisTemplate.expire(key, EXPIRATION, TimeUnit.MINUTES);
    }

    /**
     * 이메일을 key로 인증키를 찾습니다.
     *
     * @param email
     * @return
     */
    public Optional<String> findByEmail(String email) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_AUTH + email;
        return Optional.ofNullable(valueOperations.get(key));
    }

    /**
     * 이메일을 key로 삭제합니다.
     *
     * @param email 이메일
     */
    public void deleteByEmail(String email) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_AUTH + email;
        redisTemplate.delete(key);
    }
}
