package joeuncamp.dabombackend.domain.course.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.dto.EnrollDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.TicketJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.order.entity.Order;
import joeuncamp.dabombackend.domain.order.entity.Ticket;
import joeuncamp.dabombackend.global.error.exception.CAlreadyEnrolledCourse;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollService {
    private final MemberJpaRepository memberJpaRepository;
    private final EnrollJpaRepository enrollJpaRepository;
    private final CourseJpaRepository courseJpaRepository;

    public void enroll(Member member, Ticket ticket) {
        Course course = ticket.getCourse();
        if (enrollJpaRepository.findByMemberAndCourse(member, course).isPresent()) {
            throw new CAlreadyEnrolledCourse();
        }
        Enroll enroll = Enroll.builder()
                .member(member)
                .course(course)
                .endDate(LocalDateTime.now().plusMonths(ticket.getCoursePeriod().getMonth()))
                .build();
        enrollJpaRepository.save(enroll);
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
        if (enroll.isEmpty()){
            return false;
        }
        return !LocalDateTime.now().isAfter(enroll.get().getEndDate());
    }

    public boolean doesEnrolled(Member member, Course course) {
        Optional<Enroll> enroll = enrollJpaRepository.findByMemberAndCourse(member, course);
        if (enroll.isEmpty()){
            return false;
        }
        return !LocalDateTime.now().isAfter(enroll.get().getEndDate());
    }
}

