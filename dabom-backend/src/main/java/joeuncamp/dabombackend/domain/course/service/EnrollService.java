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
     * 등록 정보가 존재하거나, 해당 강좌의 크리에이터라면 true를 반환합니다.
     *
     * @param requestDto 회원, 강좌
     * @return boolean
     */
    public SingleResponseDto<Boolean> doesEnrolled(EnrollDto.Request requestDto) {
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        boolean doesEnrollExist = enrollJpaRepository.findByMemberAndCourse(member, course).isPresent();
        boolean isCreator = course.getCreatorProfile().getMember().equals(member);
        return new SingleResponseDto<>(doesEnrollExist || isCreator);
    }

    public boolean doesEnrolled(Member member, Course course) {
        boolean doesEnrollExist = enrollJpaRepository.findByMemberAndCourse(member, course).isPresent();
        boolean isCreator = course.getCreatorProfile().getMember().equals(member);
        return doesEnrollExist || isCreator;
    }
}

