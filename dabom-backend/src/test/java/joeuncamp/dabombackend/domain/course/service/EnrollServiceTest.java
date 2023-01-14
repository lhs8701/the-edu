package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.EnrollDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAlreadyEnrolledCourse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnrollServiceTest {
    @InjectMocks
    EnrollService enrollService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Mock
    CourseJpaRepository courseJpaRepository;

    @Mock
    EnrollJpaRepository enrollJpaRepository;


    @Test
    @DisplayName("이미 수강 등록된 강좌인 경우 예외가 반환된다.")
    void 수강_등록된_강좌인_경우_예외가_반환된다() {
        // given

        Member member = Member.builder()
                .id(1L)
                .build();
        Course course = Course.builder()
                .id(1L)
                .build();

        EnrollDto.Request requestDto = EnrollDto.Request.builder()
                .memberId(1L)
                .courseId(1L)
                .build();

        given(memberJpaRepository.findById(requestDto.getMemberId())).willReturn(Optional.of(member));
        given(courseJpaRepository.findById(requestDto.getCourseId())).willReturn(Optional.of(course));
        given(enrollJpaRepository.findByMemberAndCourse(member, course)).willReturn(Optional.of(new Enroll()));

        // when

        // then
        assertThatThrownBy(() -> enrollService.enroll(requestDto))
                .isInstanceOf(CAlreadyEnrolledCourse.class);
    }
}
