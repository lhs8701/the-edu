package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.course.entity.Chapter;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

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
    public static class Response{
        List<ChapterResponse> chapterList;
        public Response(){
            this.chapterList = new ArrayList<>();
        }
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
    @Getter
    @Setter
    public static class ChapterResponse{
        String title;
        List<UnitResponse> unitList;

        public ChapterResponse(){
            this.unitList = new ArrayList<>();
        }
    }
    @Getter
    public static class UnitResponse{
        Long unitId;
        String title;
        public UnitResponse(Unit unit){
            this.unitId = unit.getId();
            this.title = unit.getTitle();
        }
    }


}
