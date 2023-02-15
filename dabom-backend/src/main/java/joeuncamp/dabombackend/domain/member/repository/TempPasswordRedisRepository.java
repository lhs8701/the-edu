package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.global.constant.JwtExpiration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TempPasswordRedisRepository {
    private final StringRedisTemplate redisTemplate;
    final static String PREFIX_PASSWORD = "PASSWORD:";

    /**
     * 레디스에 임시 비밀번호를 저장합니다.
     * 30분간 유지됩니다.
     *
     * @param memberId 회원 아이디넘버 (key)
     * @param password 임시 비밀번호 (value)
     */
    public void saveTempPassword(String memberId, String password) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_PASSWORD + memberId;
        valueOperations.set(key, password);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
    }


    /**
     * 회원의 아이디넘버를 key로 발급한 임시 비밀번호를 조회합니다.
     *
     * @param memberId 회원 아이디넘버 (key)
     * @return 회원의 임시 비밀번호
     */
    public Optional<String> findByMemberId(String memberId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_PASSWORD + memberId;
        return Optional.ofNullable(valueOperations.get(key));
    }

    /**
     * 회원의 아이디넘버를 key로 발급한 임시 비밀번호를 삭제합니다.
     *
     * @param memberId 회원 아이디넘버 (key)
     */
    public void deleteByMemberId(String memberId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = PREFIX_PASSWORD + memberId;
        redisTemplate.delete(key);
    }
}
