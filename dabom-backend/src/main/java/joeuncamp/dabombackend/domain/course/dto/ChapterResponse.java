package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.course.entity.Chapter;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChapterResponse implements UnitResponse {
    @Schema(description = "챕터명", example = ExampleValue.Unit.CHAPTER)
    String title;
    @Schema(description = "챕터에 속한 강의 목록")
    List<UnitResponse> units;

    public ChapterResponse(Chapter chapter, List<UnitResponse> units) {
        this.title = chapter.getTitle();
        this.units = units;
    }
}
