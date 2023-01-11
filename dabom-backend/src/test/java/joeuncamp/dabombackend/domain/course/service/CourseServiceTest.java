package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.CourseCreationRequestDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.CreatorService;
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
}
