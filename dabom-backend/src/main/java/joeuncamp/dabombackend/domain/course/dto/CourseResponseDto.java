package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponseDto {
    @Schema(name = "아이디넘버", example = "1")
    Long id;
    @Schema(name = "제목", example = ExampleValue.Course.TITLE)
    String title;
    @Schema(name = "설명", example = ExampleValue.Course.DESCRIPTION)
    String description;
    @Schema(name = "강사", example = ExampleValue.Member.NAME)
    String instructor;
    @Schema(name = "카테고리", example = ExampleValue.Course.CATEGORY)
    CategoryType category;
    @Schema(name = "가격", example = "143000")
    long price;
    @Schema(name = "찜", example = "1500")
    long wish;

    public CourseResponseDto(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.instructor = course.getCreatorProfile().getMember().getName();
        this.category = course.getCategory();
        this.price = course.getPrice();
        this.wish = course.getWishList().size();
    }
}
