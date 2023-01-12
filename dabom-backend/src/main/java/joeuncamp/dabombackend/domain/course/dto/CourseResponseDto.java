package joeuncamp.dabombackend.domain.course.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponseDto {
    Long id;
    String title;
    String description;
    String instructor;

    CategoryType category;
    long price;

    public CourseResponseDto(Course course){
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.instructor = course.getCreatorProfile().getMember().getName();
        this.category = course.getCategory();
        this.price = course.getPrice();
    }
}
