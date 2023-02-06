package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    public static class Response{
        List<ChapterResponse> chapterList;
    }
    @Getter
    public static class ChapterRequest {
        String title;
        List<UnitRequest> unitList;
    }
    @Getter
    public static class UnitRequest {
        Long unitId;
    }
    public static class ChapterResponse{
        String title;
        List<UnitResponse> unitResponse;
    }
    public static class UnitResponse{
        Long unitId;
        String title;
        boolean completed;
    }
}
