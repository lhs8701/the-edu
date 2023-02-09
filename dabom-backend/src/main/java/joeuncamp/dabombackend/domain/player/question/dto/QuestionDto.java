package joeuncamp.dabombackend.domain.player.question.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import joeuncamp.dabombackend.domain.member.dto.ProfileDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class QuestionDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreationRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long unitId;
        @Schema(description = "질문 제목", example = ExampleValue.Question.TITLE)
        @NotEmpty
        private String title;
        @Schema(description = "질문 내용", example = ExampleValue.Question.DESCRIPTION)
        @NotEmpty
        private String content;

        public Question toEntity(Member member, Unit unit) {
            return Question.builder()
                    .title(this.title)
                    .content(this.content)
                    .unit(unit)
                    .member(member)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class GetAllRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long unitId;
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
        Long questionId;
        @Schema(description = "수정한 제목", example = ExampleValue.Question.TITLE)
        String title;
        @Schema(description = "수정한 내용", example = ExampleValue.Question.DESCRIPTION)
        String content;
    }

    @Getter
    public static class ShortResponse{
        @Schema(hidden = true, description = "질문 아이디넘버", example = "1")
        Long questionId;
        @Schema(description = "질문 제목", example = ExampleValue.Question.TITLE)
        String title;

        public ShortResponse(Question question) {
            this.questionId = question.getId();
            this.title = question.getTitle();
        }
    }

    @Getter
    public static class Response{
        @Schema(hidden = true, description = "질문 아이디넘버", example = "1")
        Long questionId;
        @Schema(description = "질문 제목", example = ExampleValue.Question.TITLE)
        String title;
        @Schema(description = "질문 내용", example = ExampleValue.Question.DESCRIPTION)
        String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "생성 시간", example = ExampleValue.Time.DATE)
        LocalDateTime createdTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "최근 수정 시간", example = ExampleValue.Time.DATE)
        LocalDateTime modifiedTime;
        @Schema(description = "작성자")
        ProfileDto.ShortResponse writer;

        public Response(Question question) {
            this.questionId = question.getId();
            this.title = question.getTitle();
            this.content = question.getContent();
            this.createdTime = question.getCreatedTime();
            this.modifiedTime = question.getModifiedTime();
            this.writer = new ProfileDto.ShortResponse(question.getMember());
        }
    }
}
