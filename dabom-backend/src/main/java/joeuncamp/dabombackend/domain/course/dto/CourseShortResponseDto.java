package joeuncamp.dabombackend.domain.course.dto;

import joeuncamp.dabombackend.domain.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseShortResponseDto {
    Long courseId;
    String title;
    String instructor;

    public CourseShortResponseDto(Course course){
        this.courseId = course.getId();
        this.title = course.getTitle();
        this.instructor = course.getCreatorProfile().getMember().getName();
    }
}
