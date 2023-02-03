package joeuncamp.dabombackend.domain.unit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MyUnitDto {

    @Getter
    @AllArgsConstructor
    public static class GetRequest {
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long courseId;
    }

    @Getter
    public static class Response{
        @Schema(description = "강의 아이디넘버", example = "1")
        Long unitId;
        @Schema(description = "순서", example = "1")
        int sequence;
        @Schema(description = "챕터 아이디넘버", example = "1")
        Long chapterId;
        @Schema(description = "강의 제목", example = ExampleValue.Unit.TITLE)
        String title;
        @Schema(description = "시청 완료 여부", example = ExampleValue.Unit.TITLE)
        boolean completed;


        public Response(Unit unit, boolean completed){
            this.unitId = unit.getId();
            this.sequence = unit.getSequence();
            this.chapterId = unit.getChapterId();
            this.title = unit.getTitle();
            this.completed = completed;
        }
    }
}
