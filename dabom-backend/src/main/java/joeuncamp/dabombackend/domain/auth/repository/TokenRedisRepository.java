package joeuncamp.dabombackend.domain.auth.repository;

import joeuncamp.dabombackend.global.constant.JwtExpiration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenRedisRepository {
    private final StringRedisTemplate redisTemplate;

    private final static String PREFIX_REFRESH = "REFRESH:";
    private final static String PREFIX_BLOCKED = "BLOCKED:";

    /**
     * 리프레시토큰을 저장합니다.
     * 'PREFIX:[리프레시토큰]'을 key로 사용합니다.
     * 회원의 계정을 value로 설정합니다.
     *
     * @param refreshToken 리프레시토큰
     * @param account      회원의 계정
     */
    public void saveRefreshToken(String refreshToken, String account) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_REFRESH + refreshToken;
        valueOperations.set(key, account);
        redisTemplate.expire(key, JwtExpiration.REFRESH_TOKEN.getTime(), TimeUnit.SECONDS);
    }

    /**
     * Block된 어세스토큰을 저장합니다.
     * 'PREFIX:[어세스토큰]'을 key로 사용합니다.
     * "empty"문자열을 value로 설정합니다.
     * 어세스토큰의 남은 시간동안 저장됩니다.
     *
     * @param accessToken 어세스토큰
     * @param remainTime  남은 시간
     */
    public void saveBlockedToken(String accessToken, Long remainTime) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_BLOCKED + accessToken;
        valueOperations.set(key, "empty");
        redisTemplate.expire(key, remainTime, TimeUnit.SECONDS);
    }

    /**
     * 리프레시토큰에 저장된 회원의 계정을 찾습니다.
     *
     * @param refreshToken 리프레시토큰
     * @return 회원의 계정
     */
    public Optional<String> findByRefreshToken(String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_REFRESH + refreshToken;
        return Optional.ofNullable(valueOperations.get(key));
    }

    /**
     * 어세스토큰이 Block되었는지 확인합니다.
     *
     * @param accessToken 어세스토큰
     * @return true/false
     */
    public boolean doesTokenBlocked(String accessToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_BLOCKED + accessToken;
        return valueOperations.get(key) != null;
    }

    /**
     * 리프레시토큰을 삭제합니다.
     *
     * @param refreshToken 리프레시토큰
     */
    public void deleteRefreshToken(String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_REFRESH + refreshToken;
        redisTemplate.delete(key);
    }
}
