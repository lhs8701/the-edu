package joeuncamp.dabombackend.domain.course.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.dto.EnrollDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.order.entity.Ticket;
import joeuncamp.dabombackend.domain.player.record.entity.Record;
import joeuncamp.dabombackend.domain.player.record.repository.RecordRedisRepository;
import joeuncamp.dabombackend.domain.player.record.repository.ViewJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAlreadyEnrolledCourse;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollService {
    private final MemberJpaRepository memberJpaRepository;
    private final EnrollJpaRepository enrollJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final RecordRedisRepository recordRedisRepository;
    private final ViewJpaRepository viewJpaRepository;

    /**
     * 수강 정보를 갱신합니다.
     * 없다면 새로 생성합니다.
     *
     * @param member 회원
     * @param ticket 수강권
     */
    public void enroll(Member member, Ticket ticket) {
        Course course = ticket.getCourse();
        Optional<Enroll> enrollOptional = enrollJpaRepository.findByMemberAndCourse(member, course);
        Enroll enroll = enrollOptional.orElseGet(() -> Enroll.builder()
                .member(member)
                .course(course)
                .build());
        enroll.setEndDate(LocalDateTime.now().plus(ticket.getDuration()));
        enrollJpaRepository.save(enroll);
    }

    /**
     * 등록 정보를 제거합니다.
     *
     * @param member 회원
     * @param ticket 티켓
     */
    public void dropOutCourse(Member member, Ticket ticket) {
        Course course = ticket.getCourse();
        Enroll enroll = enrollJpaRepository.findByMemberAndCourse(member, course).orElseThrow();
        enrollJpaRepository.delete(enroll);
        List<Record> recordList = recordRedisRepository.findByMemberIdAndCourseId(member.getId(), course.getId());
        recordRedisRepository.deleteAll(recordList);
        viewJpaRepository.deleteByMemberAndCourse(member, course);
    }

    /**
     * 등록 정보가 존재하거나, 해당 강좌의 크리에이터라면 true를 반환합니다.
     *
     * @param requestDto 회원, 강좌
     * @return boolean
     */
    public Boolean doesEnrolled(EnrollDto.Request requestDto) {
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Optional<Enroll> enroll = enrollJpaRepository.findByMemberAndCourse(member, course);
        if (enroll.isEmpty()) {
            return false;
        }
        return !LocalDateTime.now().isAfter(enroll.get().getEndDate());
    }

    public boolean doesEnrolled(Member member, Course course) {
        Optional<Enroll> enroll = enrollJpaRepository.findByMemberAndCourse(member, course);
        if (enroll.isEmpty()) {
            return false;
        }
        return !LocalDateTime.now().isAfter(enroll.get().getEndDate());
    }
}

