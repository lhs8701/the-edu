package joeuncamp.dabombackend.domain.post.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.post.dto.ReviewDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.domain.post.repository.ReviewJpaRepository;
import joeuncamp.dabombackend.global.common.IdResponseDto;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import joeuncamp.dabombackend.global.error.exception.CReviewExistException;
import joeuncamp.dabombackend.util.RoundCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewJpaRepository reviewJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final EnrollService enrollService;

    /**
     * 강좌 후기를 등록합니다.
     * 등록하지 않았을 경우 예외가 발생합니다.
     * 이미 후기를 남겼을 경우 예외가 발생합니다.
     *
     * @param createRequestDto 후기 작성 DTO
     * @return 작성된 후기 아이디넘버
     */
    public IdResponseDto writeReview(ReviewDto.CreateRequest createRequestDto) {
        Member member = memberJpaRepository.findById(createRequestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Course course = courseJpaRepository.findById(createRequestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, course)) {
            throw new CAccessDeniedException("등록하지 않은 강좌입니다.");
        }
        if (reviewJpaRepository.findByMemberAndCourse(member, course).isPresent()) {
            throw new CReviewExistException();
        }
        Long savedId = createAndSaveReview(createRequestDto, member, course);
        return new IdResponseDto(savedId);
    }

    private Long createAndSaveReview(ReviewDto.CreateRequest createRequestDto, Member member, Course course) {
        Review review = createRequestDto.toEntity(member, course);
        return reviewJpaRepository.save(review).getId();
    }

    /**
     * 강좌 내의 수강 후기 목록을 반환합니다.
     *
     * @param courseId 강좌 아이디넘버
     * @return 수강후기 목록
     */
    public List<ReviewDto.Response> getReviews(Long courseId) {
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);
        List<Review> reviews = reviewJpaRepository.findAllByCourse(course);
        return reviews.stream().map(ReviewDto.Response::new).toList();
    }

    /**
     * 수강 후기를 수정합니다.
     *
     * @param requestDto 후기, 회원, 수정내용
     * @return 수정한 후기의 아이디넘버
     */
    public Long updateReview(ReviewDto.UpdateRequest requestDto) {
        Review review = reviewJpaRepository.findById(requestDto.getReviewId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!member.equals(review.getMember())) {
            throw new CAccessDeniedException("작성자 본인만 수정할 수 있습니다.");
        }
        review.update(requestDto.getContent(), requestDto.getScore());
        reviewJpaRepository.save(review);
        return review.getId();
    }

    /**
     * 수강 후기를 삭제합니다.
     *
     * @param requestDto 회원, 삭제할 후기
     */
    public void deleteReview(ReviewDto.DeleteRequest requestDto) {
        Review review = reviewJpaRepository.findById(requestDto.getReviewId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!member.equals(review.getMember())) {
            throw new CAccessDeniedException("작성자 본인만 삭제할 수 있습니다.");
        }
        reviewJpaRepository.delete(review);
    }


    /**
     * 강좌의 평균 평점을 계산합니다.
     * 평균 평점은 반올림하여 첫번째 자리까지 나타냅니다.
     *
     * @param course 계산할 강좌
     * @return 평균 평점
     */
    public double calculateAverageScore(Course course) {
        List<Review> reviews = reviewJpaRepository.findAllByCourse(course);
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getScore();
        }
        return RoundCalculator.round(sum / reviews.size(), 1);
    }
}
