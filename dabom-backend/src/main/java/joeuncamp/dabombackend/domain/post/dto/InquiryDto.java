package joeuncamp.dabombackend.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.dto.ProfileDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.post.entity.Inquiry;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.*;

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
        @Schema(description = "강좌명", example = ExampleValue.Course.TITLE)
        String course;
        @Schema(description = "내용", example = ExampleValue.Post.CONTENT)
        String content;
        @Schema(description = "좋아요", example = "1150")
        int likes;
        @Schema(description = "작성자")
        ProfileDto.ShortResponse writer;
        @Schema(description = "댓글")
        ReplyDto.Response reply;

        public Response(Inquiry inquiry){
            this.inquiryId = inquiry.getId();
            this.course = inquiry.getCourse().getTitle();
            this.content = inquiry.getContent();
            this.likes = inquiry.getLikes();
            this.writer = new ProfileDto.ShortResponse(inquiry.getMember());
            if (inquiry.getReply() != null) {
                this.reply = new ReplyDto.Response(inquiry.getReply());
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateRequest {
        @Schema(hidden = true, description = "회원 아이디넘버")
        Long memberId;
        @Schema(hidden = true, description = "문의 아이디넘버")
        Long inquiryId;
        @NotNull
        @Schema(description = "내용", example = ExampleValue.Post.CONTENT)
        String content;
    }

    @Getter
    @AllArgsConstructor
    public static class DeleteRequest {
        @Schema(hidden = true, description = "회원 아이디넘버")
        Long memberId;
        @Schema(hidden = true, description = "문의 아이디넘버")
        Long inquiryId;
    }
}