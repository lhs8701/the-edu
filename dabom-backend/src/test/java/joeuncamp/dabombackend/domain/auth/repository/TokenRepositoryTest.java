package joeuncamp.dabombackend.domain.auth.repository;

import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
public class TokenRepositoryTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    @DisplayName("리프레시 토큰을 생성하고, 저장한다.")
    void 리프레시_토큰을_생성하고_저장한다() {
        // given
        String refreshToken = "abcdefg12345678";
        String account = ExampleValue.Member.ACCOUNT;
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        // when
        valueOperations.set(refreshToken, account);
        redisTemplate.expire(refreshToken, 20L, TimeUnit.SECONDS);

        String foundAccount = valueOperations.get(refreshToken);

        // then
        assertThat(foundAccount).isEqualTo(account);
    }

    @Test
    @DisplayName("저장된 리프레시 토큰을 삭제한다.")
    void 저장된_리프레시_토큰을_삭제한다() {
        // given
        String refreshToken = "12345678abcdefg";
        String account = ExampleValue.Member.ACCOUNT;
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken, account);
        redisTemplate.expire(refreshToken, 20L, TimeUnit.DAYS);

        // when
        redisTemplate.delete(refreshToken);

        String foundAccount = valueOperations.get(refreshToken);

        // then
        assertThat(foundAccount).isNull();
    }
}
