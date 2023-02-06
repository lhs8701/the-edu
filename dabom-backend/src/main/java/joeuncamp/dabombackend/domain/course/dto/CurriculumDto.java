package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
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
        List<ChapterRequest> chapterList;
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
    public static class Response{
        List<ChapterResponse> chapterList;
        public Response(){
            this.chapterList = new ArrayList<>();
        }
    }
    @Getter
    public static class StatusResponse {
        List<MyChapterResponse> chapterList;
        public StatusResponse(){
            this.chapterList = new ArrayList<>();
        }
    }

    @Getter
    public static class ChapterRequest {
        @Schema(description = "챕터명", example = ExampleValue.Unit.CHAPTER)
        String title;
        List<UnitRequest> unitList;
    }

    @Getter
    public static class UnitRequest {
        Long unitId;
    }
    @Getter
    @Setter
    public static class ChapterResponse{
        @Schema(description = "챕터명", example = ExampleValue.Unit.CHAPTER)
        String title;
        List<UnitResponse> unitList;

        public ChapterResponse(){
            this.unitList = new ArrayList<>();
        }
    }
    @Getter
    @Setter
    public static class MyChapterResponse{
        @Schema(description = "챕터명", example = ExampleValue.Unit.CHAPTER)
        String title;
        List<MyUnitResponse> unitList;

        public MyChapterResponse(){
            this.unitList = new ArrayList<>();
        }
    }
    @Getter
    public static class UnitResponse{
        Long unitId;
        @Schema(description = "강의 제목", example = ExampleValue.Unit.TITLE)
        String title;
        public UnitResponse(Unit unit){
            this.unitId = unit.getId();
            this.title = unit.getTitle();
        }
    }

    @Getter
    public static class MyUnitResponse{
        Long unitId;
        @Schema(description = "강의 제목", example = ExampleValue.Unit.TITLE)
        String title;
        @Schema(description = "시청 완료 여부")
        boolean completed;
        public MyUnitResponse(Unit unit, boolean completed){
            this.unitId = unit.getId();
            this.title = unit.getTitle();
            this.completed = completed;
        }
    }
}
