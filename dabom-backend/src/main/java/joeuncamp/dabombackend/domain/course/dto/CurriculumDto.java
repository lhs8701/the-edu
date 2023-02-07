package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CurriculumDto {
    @Getter
    @Setter
    public static class CreateRequest{
        @Schema(hidden = true)
        Long courseId;
        @Schema(hidden = true)
        Long memberId;
        List<ChapterRequest> chapters;
    }

    @Getter
    @AllArgsConstructor
    public static class GetRequest {
        @Schema(hidden = true)
        Long courseId;
    }

    @Getter
    @AllArgsConstructor
    public static class StatusRequest {
        @Schema(hidden = true)
        Long courseId;
        @Schema(hidden = true)
        Long memberId;
    }

    @Getter
    public static class ChapterRequest {
        @Schema(description = "챕터명", example = ExampleValue.Unit.CHAPTER)
        String title;
        List<UnitRequest> units;
    }

    @Getter
    public static class UnitRequest {
        Long unitId;
    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        List<ChapterResponse> chapters;
        public Response(){
            this.chapters = new ArrayList<>();
        }
    }
    @Getter
    @AllArgsConstructor
    public static class StatusResponse {
        MyCourseDto.Response courseStatus;
        List<ChapterStatusResponse> chapters;
    }

    @Getter
    @Setter
    public static class ChapterResponse {
        @Schema(description = "챕터명", example = ExampleValue.Unit.CHAPTER)
        String title;
        List<UnitResponse> units;

        public ChapterResponse(){
            this.units = new ArrayList<>();
        }
    }
    @Getter
    @Setter
    public static class ChapterStatusResponse {
        @Schema(description = "챕터명", example = ExampleValue.Unit.CHAPTER)
        String title;
        List<UnitStatusResponse> units;

        public ChapterStatusResponse(){
            this.units = new ArrayList<>();
        }
    }
    @Getter
    public static class UnitResponse {
        Long unitId;
        @Schema(description = "강의 제목", example = ExampleValue.Unit.TITLE)
        String title;
        public UnitResponse(joeuncamp.dabombackend.domain.unit.entity.Unit unit){
            this.unitId = unit.getId();
            this.title = unit.getTitle();
        }
    }

    @Getter
    public static class UnitStatusResponse {
        Long unitId;
        @Schema(description = "강의 제목", example = ExampleValue.Unit.TITLE)
        String title;
        @Schema(description = "시청 완료 여부")
        boolean completed;
        public UnitStatusResponse(joeuncamp.dabombackend.domain.unit.entity.Unit unit, boolean completed){
            this.unitId = unit.getId();
            this.title = unit.getTitle();
            this.completed = completed;
        }
    }
}
