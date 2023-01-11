package joeuncamp.dabombackend.domain.course.dto;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCreationRequestDto {
    String title;
    String description;
    String majorCategory;
    String subCategory;
    long price;

    public Course toEntity() {
        return Course.builder()
                .title(title)
                .description(description)
                .majorCategory(majorCategory)
                .subCategory(subCategory)
                .price(price)
                .build();
    }
}
