package joeuncamp.dabombackend.domain.player.record.service;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.record.dto.RecordDto;
import joeuncamp.dabombackend.domain.player.record.entity.Record;
import joeuncamp.dabombackend.domain.player.record.repository.RecordRedisRepository;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordService {
    private final MemberJpaRepository memberJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    private final RecordRedisRepository recordRedisRepository;

    /**
     * 강의 시청 기록을 저장합니다.
     *
     * @param requestDto 회원, 강의, 시간
     */
    public void saveRecord(RecordDto.SaveRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Record record = Record.builder()
                .memberId(member.getId())
                .unitId(unit.getId())
                .courseId(unit.getCourse().getId())
                .time(requestDto.getTime())
                .build();
        Optional<Record> found = recordRedisRepository.findByMemberIdAndUnitId(member.getId(), unit.getId());
        found.ifPresent(recordRedisRepository::delete);
        recordRedisRepository.save(record);
    }

    /**
     * 마지막으로 시청한 지점을 반환합니다.
     *
     * @param requestDto 회원, 강의
     * @return 시청 정보
     */
    public double getTime(RecordDto.GetRequest requestDto) {
        Record record = recordRedisRepository.findByMemberIdAndUnitId(requestDto.getMemberId(), requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        return record.getTime();
    }

    /**
     * 가장 최근에 시청했던 강의 정보를 반환합니다.
     * 없다면, 강좌의 가장 첫번째 강의를 반환합니다.
     *
     * @param member 회원
     * @param course 강좌
     * @return 최근 시청한 강의
     */
    public RecordDto.Response getRecentPlayedUnit(Member member, Course course) {
        Optional<Record> record = recordRedisRepository.findTop1ByMemberIdAndCourseIdOrderByRecentTimeDesc(member.getId(), course.getId());
        if (record.isEmpty()) {
            return new RecordDto.Response(course.getUnitList().get(0).getId(), 0);
        }
        return new RecordDto.Response(record.get());
    }
}
