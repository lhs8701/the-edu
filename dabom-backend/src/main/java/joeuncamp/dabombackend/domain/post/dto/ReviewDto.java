package joeuncamp.dabombackend.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.dto.ProfileDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.*;

public class ReviewDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateRequest {
        @Schema(hidden = true, description = "회원 아이디넘버", example = "1")
        Long memberId;
        @Schema(hidden = true, description = "강좌 아이디넘버", example = "1")
        Long courseId;
        @NotNull
        @Schema(description = "내용", example = ExampleValue.Post.CONTENT)
        String content;
        @NotNull
        @Schema(description = "평점")
        int score;

        public Review toEntity(Member member, Course course) {
            return Review.builder()
                    .member(member)
                    .course(course)
                    .content(content)
                    .score(score)
                    .build();
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateRequest {
        @Schema(hidden = true, description = "회원 아이디넘버")
        Long memberId;
        @Schema(hidden = true, description = "후기 아이디넘버")
        Long reviewId;
        @NotNull
        @Schema(description = "내용", example = ExampleValue.Post.CONTENT)
        String content;
        @NotNull
        @Schema(description = "평점")
        int score;
    }

    @Getter
    @AllArgsConstructor
    public static class DeleteRequest {
        @Schema(hidden = true, description = "회원 아이디넘버")
        Long memberId;
        @Schema(hidden = true, description = "후기 아이디넘버")
        Long reviewId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        @Schema(description = "아이디넘버", example = "1")
        Long reviewId;
        @Schema(description = "강좌명", example = ExampleValue.Course.TITLE)
        String course;
        @Schema(description = "내용", example = ExampleValue.Post.CONTENT)
        String content;
        @Schema(description = "좋아요", example = "1150")
        int likes;
        @Schema(description = "평점", example = "5")
        int rating;
        @Schema(description = "작성자")
        ProfileDto.ShortResponse writer;

        public Response(Review review) {
            this.reviewId = review.getId();
            this.course = review.getCourse().getTitle();
            this.content = review.getContent();
            this.likes = review.getLikes();
            this.rating = review.getScore();
            this.writer = new ProfileDto.ShortResponse(review.getMember());
        }
    }
}