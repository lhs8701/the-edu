package joeuncamp.dabombackend.domain.course.dto;

import joeuncamp.dabombackend.domain.course.entity.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseThumbnailResponseDto {
    Long courseId;
    String title;
    String instructor;

    public CourseThumbnailResponseDto(Course course){
        this.courseId = course.getId();
        this.title = course.getTitle();
        this.instructor = course.getCreatorProfile().getMember().getName();
    }
}
