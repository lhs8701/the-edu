package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Getter
@AllArgsConstructor
@Builder
public class MyCourseShortResponseDto {

    @Schema(name = "아이디넘버", example = "1")
    Long id;
    @Schema(name = "제목", example = ExampleValue.Course.TITLE)
    String title;
    @Schema(name = "강사", example = ExampleValue.Member.NAME)
    String instructor;

    public MyCourseShortResponseDto(Course course){
        this.id = course.getId();
        this.title = course.getTitle();
        this.instructor = course.getInstructorName();
    }
}
