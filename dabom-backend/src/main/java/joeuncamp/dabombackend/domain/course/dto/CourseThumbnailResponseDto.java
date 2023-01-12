package joeuncamp.dabombackend.domain.course.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseThumbnailResponseDto {
    Long courseId;
    String title;
    String instructor;
}
