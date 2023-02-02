package joeuncamp.dabombackend.domain.player.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        @Schema(description = "타임라인", example = "13.2")
        @NotEmpty
        private int timeline;

        public Question toEntity(Member member, Unit unit) {
            return Question.builder()
                    .title(this.title)
                    .content(this.content)
                    .timeline(this.timeline)
                    .unit(unit)
                    .member(member)
                    .build();
        }
    }
}
