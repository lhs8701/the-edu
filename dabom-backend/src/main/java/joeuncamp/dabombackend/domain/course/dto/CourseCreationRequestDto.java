package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.global.constant.ExampleValue;
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
    @NotBlank
    @Schema(description = "강좌 제목", example = ExampleValue.Course.TITLE)
    String title;
    @NotBlank
    @Schema(description = "강좌 설명", example = ExampleValue.Course.DESCRIPTION)
    String description;
    @NotBlank
    @Category
    @Schema(description = "세부 카테고리", example = ExampleValue.Course.CATEGORY)
    String category;
    @NotNull
    @PositiveOrZero
    @Schema(description = "가격", example = "143000")
    long price;

    public Course toEntity(CreatorProfile creatorProfile) {
        CategoryType categoryType = CategoryType.findByTitle(category);
        if (categoryType == CategoryType.EMPTY) {
            throw new CIllegalArgumentException();
        }

        Course course = Course.builder()
                .title(title)
                .description(description)
                .category(categoryType)
                .price(price)
                .build();
        course.setCreatorProfile(creatorProfile);
        return course;
    }
}
