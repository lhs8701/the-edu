package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.CourseCreationRequestDto;
import joeuncamp.dabombackend.domain.course.dto.CourseResponseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.CreatorService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.error.exception.CCreationDeniedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @InjectMocks
    CourseService courseService;

    @Mock
    CreatorService creatorService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Mock
    CourseJpaRepository courseJpaRepository;

    @Test
    @DisplayName("크리에이터가 아닌 사람이 강좌를 개설할 경우 예외가 발생한다.")
    void 크리에이터가_아닌_사람이_강좌를_개설할_경우_예외가_발생한다() {
        // given
        CourseCreationRequestDto dto = CourseCreationRequestDto.builder().build();
        Member member = Member.builder().build();
        Long memberId = 1L;
        given(memberJpaRepository.findById(memberId)).willReturn(Optional.of(member));
        given(creatorService.hasCreatorProfile(member)).willReturn(false);

        // when

        // then
        Assertions.assertThatThrownBy(() -> courseService.openCourse(dto, memberId))
                .isInstanceOf(CCreationDeniedException.class);
    }

    @Test
    @DisplayName("강좌 단건 조회 시, 강좌 정보에 강사의 실명이 포함된다")
    void 강좌_정보에_강사_이름이_포함된다() {
        // given
        Long courseId = 1L;

        Member instructor = Member.builder()
                .nickname(ExampleValue.Member.NICKNAME)
                .build();
        CreatorProfile creatorProfile = CreatorProfile.builder()
                .member(instructor)
                .build();
        Course course = Course.builder()
                .creatorProfile(creatorProfile)
                .build();
        instructor.activateCreatorProfile(creatorProfile);

        given(courseJpaRepository.findById(courseId)).willReturn(Optional.of(course));

        // when
        CourseResponseDto responseDto = courseService.getCourse(courseId);

        // then
        assertThat(responseDto.getInstructor()).isEqualTo(instructor.getNickname());
    }
}
