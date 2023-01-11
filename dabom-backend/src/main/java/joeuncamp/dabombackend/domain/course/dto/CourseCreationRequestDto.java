package joeuncamp.dabombackend.domain.course.dto;

import jakarta.validation.constraints.PositiveOrZero;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import joeuncamp.dabombackend.global.validation.Category;
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
    @Category
    String category;
    @PositiveOrZero
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
