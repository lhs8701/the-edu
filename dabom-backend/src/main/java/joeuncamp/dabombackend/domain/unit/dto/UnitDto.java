package joeuncamp.dabombackend.domain.unit.dto;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UnitDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UploadRequest{
        Long courseId;
        String title;
        String description;

        public Unit toEntity(Course course){
            return Unit.builder()
                    .title(title)
                    .description(description)
                    .course(course)
                    .build();
        }
    }
}
