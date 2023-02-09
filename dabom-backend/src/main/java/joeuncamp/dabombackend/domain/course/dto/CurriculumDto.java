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
    public static class CreateRequest {
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
    public static class Response {
        List<ChapterResponse> chapters;
        CourseDto.StatusResponse courseStatus;

        public Response(List<ChapterResponse> chapters) {
            this.chapters = chapters;
            this.courseStatus = null;
        }

        public Response(List<ChapterResponse> chapters, CourseDto.StatusResponse courseStatus) {
            this.chapters = chapters;
            this.courseStatus = courseStatus;
        }
    }
}
