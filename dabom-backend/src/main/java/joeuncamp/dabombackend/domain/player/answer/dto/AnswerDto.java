package joeuncamp.dabombackend.domain.player.answer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import joeuncamp.dabombackend.domain.member.dto.ProfileDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.answer.entity.Answer;
import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class AnswerDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreationRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long questionId;
        @Schema(description = "답변 내용", example = ExampleValue.Answer.DESCRIPTION)
        @NotEmpty
        private String content;

        public Answer toEntity(Member member, Question question) {
            return Answer.builder()
                    .content(this.content)
                    .member(member)
                    .question(question)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class GetRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long questionId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long answerId;
        @NotEmpty
        @Schema(description = "수정한 내용", example = ExampleValue.Answer.DESCRIPTION)
        String content;
    }

    @Getter
    @AllArgsConstructor
    public static class DeleteRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long answerId;
    }

    @Getter
    public static class Response{
        @Schema(hidden = true, description = "답변 아이디넘버", example = "1")
        Long answerId;
        @Schema(description = "답변 내용", example = ExampleValue.Question.DESCRIPTION)
        String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd.HH:mm", timezone = "Asia/Seoul")
        @Schema(description = "생성 시간", example = ExampleValue.Time.DATE)
        LocalDateTime createdTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd.HH:mm", timezone = "Asia/Seoul")
        @Schema(description = "최근 수정 시간", example = ExampleValue.Time.DATE)
        LocalDateTime modifiedTime;
        @Schema(description = "작성자")
        ProfileDto.ShortResponse writer;

        public Response(Answer answer) {
            this.answerId = answer.getId();
            this.content = answer.getContent();
            this.createdTime = answer.getCreatedTime();
            this.modifiedTime = answer.getModifiedTime();
            this.writer = new ProfileDto.ShortResponse(answer.getMember());
        }
    }
}
