package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RankingDto {
    @Schema(description = "카테고리", example = ExampleValue.Course.CATEGORY)
    String category;
    List<CourseDto.ShortResponse> courseList;
}
