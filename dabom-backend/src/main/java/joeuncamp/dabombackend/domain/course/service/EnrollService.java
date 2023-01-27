package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.EnrollDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.common.SingleResponseDto;
import joeuncamp.dabombackend.global.error.exception.CAlreadyEnrolledCourse;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollService {
    private final MemberJpaRepository memberJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final EnrollJpaRepository enrollJpaRepository;

    /**
     * 강좌를 수강 등록 합니다.
     *
     * @param requestDto 등록할 회원과 등록할 강좌
     */
    public void enroll(EnrollDto.Request requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Course course = courseJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (enrollJpaRepository.findByMemberAndCourse(member, course).isPresent()) {
            throw new CAlreadyEnrolledCourse();
        }
        createAndSaveEnroll(member, course);
    }

    private void createAndSaveEnroll(Member member, Course course) {
        Enroll enroll = Enroll.builder()
                .member(member)
                .course(course)
                .build();
        enrollJpaRepository.save(enroll);
    }

    /**
     * 강좌 수강 등록 여부를 조회합니다.
     *
     * @param courseId 강좌
     * @param memberId 회원
     * @return true/false
     */
    public SingleResponseDto<Boolean> doesEnrolled(Long courseId, Long memberId) {
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);
        return new SingleResponseDto<>(enrollJpaRepository.findByMemberAndCourse(member, course).isPresent());
    }

    public boolean doesEnrolled(Member member, Course course) {
        return enrollJpaRepository.findByMemberAndCourse(member, course).isPresent();
    }
}

