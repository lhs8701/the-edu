package joeuncamp.dabombackend.domain.player.record.repository;

import joeuncamp.dabombackend.domain.player.record.entity.Record;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecordRedisRepository extends CrudRepository<Record, Long> {
    Optional<Record> findTop1ByMemberIdAndCourseIdOrderByRecentTimeDesc(Long memberId, Long courseId);
    void deleteByMemberIdAndUnitId(Long memberId, Long unitId);
    Optional<Record> findByMemberIdAndUnitId(Long memberId, Long unitId);

}
