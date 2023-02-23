package joeuncamp.dabombackend.domain.player.record.repository;

import joeuncamp.dabombackend.domain.player.record.entity.Record;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface RecordRedisRepository extends CrudRepository<Record, Long> {
    Optional<Record> findByMemberIdAndUnitId(Long memberId, Long unitId);
    List<Record> findByMemberIdOrderByRecentTimeDesc(Long memberId);
    Optional<Record> findByMemberIdAndCourseIdOrderByRecentTimeDesc(Long memberId, Long courseId);
    List<Record> findByMemberIdAndCourseId(Long memberId, Long courseId);

    void deleteByMemberIdAndCourseId(Long memberId, Long courseId);
}
