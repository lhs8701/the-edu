package joeuncamp.dabombackend.domain.course.dto;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
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
    String category;
    long price;

    public Course toEntity() {
        CategoryType categoryType = CategoryType.findByTitle(category);
        if (categoryType == CategoryType.EMPTY){
            throw new CIllegalArgumentException();
        }

        return Course.builder()
                .title(title)
                .description(description)
                .category(categoryType)
                .price(price)
                .build();
    }
}
