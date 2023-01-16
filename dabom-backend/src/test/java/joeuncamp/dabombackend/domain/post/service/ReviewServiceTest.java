package joeuncamp.dabombackend.domain.post.service;

import joeuncamp.dabombackend.domain.course.dto.ReviewDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.domain.post.repository.PostJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @InjectMocks
    ReviewService reviewService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Mock
    CourseJpaRepository courseJpaRepository;

    @Mock
    PostJpaRepository<Review> reviewJpaRepository;

    @Mock
    EnrollService enrollService;

    @Test
    @DisplayName("후기 작성 시, 등록한 강의가 아닐 경우 예외가 발생한다")
    void 등록한_강의가_아닐경우_예외가_발생한다() {
        // given
        Member member = Member.builder().build();
        Course course = Course.builder().build();
        ReviewDto.Request requestDto = ReviewDto.Request.builder()
                .memberId(1L)
                .courseId(1L)
                .build();
        given(memberJpaRepository.findById(1L)).willReturn(Optional.of(member));
        given(courseJpaRepository.findById(1L)).willReturn(Optional.of(course));
        given(enrollService.isEnrolled(member, course)).willReturn(false);

        // when

        // then
        Assertions.assertThatThrownBy(() -> reviewService.writeReview(requestDto))
                .isInstanceOf(CAccessDeniedException.class);

    }
}
