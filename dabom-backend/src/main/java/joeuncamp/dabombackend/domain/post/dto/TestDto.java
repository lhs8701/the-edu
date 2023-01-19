package joeuncamp.dabombackend.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TestDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        @NotNull
        @Schema(description = "회원 아이디넘버", example = "1")
        Long memberId;
        @NotNull
        @Schema(description = "강좌 아이디넘버", example = "1")
        Long courseId;

        @NotNull
        @Schema(description = "내용", example = ExampleValue.Post.CONTENT)
        String content;

        int score;

        public Review toEntity(Member member, Course course){
            return Review.builder()
                    .member(member)
                    .course(course)
                    .content(content)
                    .score(score)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        @Schema(description = "아이디넘버", example = "1")
        Long reviewId;

        @Schema(description = "작성자명", example = ExampleValue.Member.NAME)
        String writer;

        @Schema(description = "강좌명", example = ExampleValue.Course.TITLE)
        String course;

        @Schema(description = "내용", example = ExampleValue.Post.CONTENT)
        String content;

        @Schema(description = "좋아요", example = "1150")
        int likes;

        @Schema(description = "평점", example = "5")
        int rating;

        public Response(Review review){
            this.reviewId = review.getId();
            this.writer = review.getMember().getName();
            this.course = review.getCourse().getTitle();
            this.content = review.getContent();
            this.likes = review.getLikes();
            this.rating = review.getScore();
        }
    }

}