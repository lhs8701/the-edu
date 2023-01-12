package joeuncamp.dabombackend.domain.course.dto;

import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.entity.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseResponseDto {
    String instructor;

    public CourseResponseDto(Course course){
        this.instructor = course.getCreatorProfile().getMember().getNickname();
    }
}
