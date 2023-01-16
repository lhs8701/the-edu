package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.post.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewDto {
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
        @Schema(description = "후기 내용", example = "1")
        String content;

        int rating;

        public Review toEntity(Member member, Course course){
            return Review.builder()
                    .member(member)
                    .course(course)
                    .content(content)
                    .rating(rating)
                    .build();
        }
    }

}