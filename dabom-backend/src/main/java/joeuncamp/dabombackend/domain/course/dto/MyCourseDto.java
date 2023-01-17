package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MyCourseDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ShortResponse{
        @Schema(description = "아이디넘버", example = "1")
        Long courseId;
        @Schema(description = "제목", example = ExampleValue.Course.TITLE)
        String title;
        @Schema(description = "강사", example = ExampleValue.Member.NAME)
        String instructor;

        public ShortResponse(Course course){
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.instructor = course.getInstructorName();
        }
    }
}