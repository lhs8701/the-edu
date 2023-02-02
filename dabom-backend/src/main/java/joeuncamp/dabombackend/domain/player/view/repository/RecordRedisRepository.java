package joeuncamp.dabombackend.domain.player.view.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecordRedisRepository {
    private final StringRedisTemplate redisTemplate;
    private final static String PREFIX_VIEW = "RECORD:";

    /**
     * 강의 시청시간을 저장합니다.
     *
     * @param unitId 강의 아이디넘버
     * @param memberId 회원 아이디넘버
     * @param time 시청 시간
     */
    public void saveRecord(String memberId, String unitId, String time) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String key = PREFIX_VIEW + unitId;
        hashOperations.put(key, memberId, time);
    }

    /**
     * 마지막 강의 시청시간을 조회합니다.
     * @param unitId 강의 아이디넘버
     * @param memberId 회원 아이디넘버
     * @return
     */
    public String getTimeFromRecord(String memberId, String unitId){
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String key = PREFIX_VIEW + unitId;
        return hashOperations.get(key, memberId);
    }
}
