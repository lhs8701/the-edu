package joeuncamp.dabombackend.domain.player.view.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
public class ViewRepositoryTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    @DisplayName("시청기록을 저장한다.")
    void 리프레시_토큰을_생성하고_저장한다() {
        // given
        String unitId = "1";
        String memberId = "1";
        String time = "16.4";
        String key = "VIEW:" + unitId;
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        // when
        hashOperations.put(key, memberId, time);

        String foundedTime = hashOperations.get(key, memberId);

        // then
        assertThat(foundedTime).isEqualTo(time);
    }

    @Test
    @DisplayName("저장된 시청기록을 삭제한다.")
    void 저장된_시청기록을_삭제한다() {
        // given
        String unitId = "1";
        String memberId = "1";
        String time = "16.4";
        String key = "VIEW:" + unitId;
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, memberId, time);

        // when
        hashOperations.delete(key, memberId);
        String foundedTime = hashOperations.get(key, memberId);

        // then
        assertThat(foundedTime).isNull();
    }
}
