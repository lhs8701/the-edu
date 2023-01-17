package joeuncamp.dabombackend.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.post.entity.Inquiry;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class InquiryDto {
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

        public Inquiry toEntity(Member member, Course course){
            return Inquiry.builder()
                    .member(member)
                    .course(course)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        @Schema(description = "아이디넘버", example = "1")
        Long inquiryId;

        @Schema(description = "작성자명", example = ExampleValue.Member.NAME)
        String writer;

        @Schema(description = "강좌명", example = ExampleValue.Course.TITLE)
        String course;

        @Schema(description = "내용", example = ExampleValue.Post.CONTENT)
        String content;

        @Schema(description = "좋아요", example = "1150")
        int likes;

        public Response(Inquiry inquiry){
            this.inquiryId = inquiry.getId();
            this.writer = inquiry.getMember().getName();
            this.course = inquiry.getCourse().getTitle();
            this.content = inquiry.getContent();
            this.likes = inquiry.getLikes();
        }
    }

}