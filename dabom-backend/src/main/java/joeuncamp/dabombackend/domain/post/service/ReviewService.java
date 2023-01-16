package joeuncamp.dabombackend.domain.post.service;

import joeuncamp.dabombackend.domain.course.dto.ReviewDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.domain.post.repository.PostJpaRepository;
import joeuncamp.dabombackend.global.common.IdResponseDto;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final PostJpaRepository<Review> reviewJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final CourseJpaRepository courseJpaRepository;

    private final EnrollService enrollService;

    /**
     * 강좌 후기를 등록합니다.
     * 등록하지 않았을 경우 예외가 발생합니다.
     *
     * @param requestDto 후기 작성 DTO
     * @return 작성된 후기 아이디넘버
     */
    public IdResponseDto writeReview(ReviewDto.Request requestDto){
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.isEnrolled(member, course)){
            throw new CAccessDeniedException();
        }
        Long savedId = createAndSaveReview(requestDto, member, course);
        return new IdResponseDto(savedId);
    }

    private Long createAndSaveReview(ReviewDto.Request requestDto, Member member, Course course) {
        Review review = requestDto.toEntity(member, course);
        return reviewJpaRepository.save(review).getId();
    }

    /**
     * 강좌 내의 수강 후기 목록을 반환합니다.
     * @param courseId 강좌 아이디넘버
     * @return 수강후기 목록
     */
    public List<ReviewDto.Response> getReviews(Long courseId){
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);
        List<Review> reviews = reviewJpaRepository.findAllByCourse(course);
        return reviews.stream().map(ReviewDto.Response::new).toList();
    }


    public double calculateAverageScore(Course course){
        List<Review> reviews = reviewJpaRepository.findAllByCourse(course);
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getScore();
        }
        return sum / reviews.size();
    }
}
