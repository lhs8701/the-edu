package joeuncamp.dabombackend.domain.post.service;

import joeuncamp.dabombackend.domain.post.dto.ReviewDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.domain.post.repository.ReviewJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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
    ReviewJpaRepository reviewJpaRepository;

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
        assertThatThrownBy(() -> reviewService.writeReview(requestDto))
                .isInstanceOf(CAccessDeniedException.class);

    }

    @Test
    @DisplayName("수강 후기 목록을 조회한다.")
    void 수강_후기_목록을_조회한다() {
        // given
        Member member = Member.builder().build();
        Course course = Course.builder().build();
        Review review = Review.builder()
                .member(member)
                .course(course)
                .build();
        List<Review> reviews = List.of(review);

        given(courseJpaRepository.findById(1L)).willReturn(Optional.of(course));
        given(reviewJpaRepository.findAllByCourse(course)).willReturn(reviews);

        // when
        List<ReviewDto.Response> responseDto = reviewService.getReviews(1L);

        // then
        assertThat(responseDto).isNotNull();
    }

    @ParameterizedTest
    @CsvSource({"1,2,3,2", "3,3,4,3.3"})
    @DisplayName("평점 평균을 계산한다.")
    void 평점_평균을_계산한다(int score1, int score2, int score3, double expected) {
        // given
        Member member = Member.builder().build();
        Course course = Course.builder().build();
        Review r1 = Review.builder()
                .score(score1)
                .member(member)
                .course(course)
                .build();
        Review r2 = Review.builder()
                .score(score2)
                .member(member)
                .course(course)
                .build();
        Review r3 = Review.builder()
                .score(score3)
                .member(member)
                .course(course)
                .build();
        List<Review> reviews = List.of(r1, r2, r3);
        given(reviewJpaRepository.findAllByCourse(course)).willReturn(reviews);

        // when
        double result = reviewService.calculateAverageScore(course);

        // then
        assertThat(result).isEqualTo(expected);
    }



}
