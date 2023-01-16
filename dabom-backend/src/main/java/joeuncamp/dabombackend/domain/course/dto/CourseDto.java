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

public class CourseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreationRequest {
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

        public Course toEntity(CreatorProfile creator) {
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
            course.setCreatorProfile(creator);
            return course;
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ShortResponse {
        @Schema(name = "아이디넘버", example = "1")
        Long courseId;
        @Schema(name = "제목", example = ExampleValue.Course.TITLE)
        String title;
        @Schema(name = "강사", example = ExampleValue.Member.NAME)
        String instructor;

        public ShortResponse(Course course) {
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.instructor = course.getInstructorName();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
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

        @Schema(name = "평점", example = "3.5")
        double score;
        @Schema(name = "가격", example = "143000")
        long price;

        @Schema(name = "찜", example = "1500")
        long wish;

        public Response(Course course, double averageScore) {
            this.id = course.getId();
            this.title = course.getTitle();
            this.description = course.getDescription();
            this.instructor = course.getInstructorName();
            this.category = course.getCategory();
            this.score = averageScore;
            this.price = course.getPrice();
            this.wish = course.getWishList().size();
        }
    }
}
