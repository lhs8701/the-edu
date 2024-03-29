package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.RankedCourse;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.validation.CategoryValidation;
import lombok.*;

import java.util.List;

public class CourseDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreationRequest {
        @Schema(hidden = true)
        Long memberId;
        @NotBlank
        @Schema(description = "강좌 제목", example = ExampleValue.Course.TITLE)
        String title;
        @NotBlank
        @Schema(description = "강좌 설명", example = ExampleValue.Course.DESCRIPTION)
        String description;
        @NotBlank
        @CategoryValidation
        @Schema(description = "세부 카테고리", example = ExampleValue.Course.CATEGORY)
        String category;
        @NotBlank
        @Schema(description = "썸네일 이미지 URL", example = ExampleValue.Image.THUMBNAIL)
        String thumbnailImage;
        @Schema(description = "소개 이미지 URL")
        List<String> descriptionImageUrls;


        public Course toEntity(CreatorProfile creator) {
            CategoryType categoryType = CategoryType.findByTitle(category);
            Course course = Course.builder()
                    .title(title)
                    .description(description)
                    .category(categoryType)
                    .thumbnailImage(new ImageInfo(thumbnailImage))
                    .descriptionImages(descriptionImageUrls.stream()
                            .map(ImageInfo::new)
                            .toList())
                    .active(false)
                    .build();
            course.setCreatorProfile(creator);
            return course;
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ShortResponse {
        @Schema(description = "아이디넘버", example = "1")
        Long courseId;
        @Schema(description = "제목", example = ExampleValue.Course.TITLE)
        String title;
        @Schema(description = "강사", example = ExampleValue.Member.NAME)
        String instructor;
        @Schema(description = "카테고리", example = ExampleValue.Course.CATEGORY)
        String category;
        @Schema(description = "썸네일 이미지")
        ImageInfo thumbnailImage;

        public ShortResponse(Course course) {
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.instructor = course.getInstructorName();
            this.category = course.getCategory().getTitle();
            this.thumbnailImage = course.getThumbnailImage();
        }

        public ShortResponse(RankedCourse rankedCourse) {
            this.courseId = rankedCourse.getId();
            this.title = rankedCourse.getTitle();
            this.instructor = rankedCourse.getInstructor();
            this.category = rankedCourse.getCategory().getTitle();
            this.thumbnailImage = rankedCourse.getThumbnailImage();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class CreatorResponse {
        @Schema(description = "아이디넘버", example = "1")
        Long courseId;
        @Schema(description = "제목", example = ExampleValue.Course.TITLE)
        String title;
        @Schema(description = "강사", example = ExampleValue.Member.NAME)
        String instructor;
        @Schema(description = "카테고리", example = ExampleValue.Course.CATEGORY)
        String category;
        @Schema(description = "썸네일 이미지")
        ImageInfo thumbnailImage;
        @Schema(description = "활성화 여부")
        boolean active;

        public CreatorResponse(Course course) {
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.instructor = course.getInstructorName();
            this.category = course.getCategory().getTitle();
            this.thumbnailImage = course.getThumbnailImage();
            this.active = course.isActive();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        @Schema(description = "아이디넘버", example = "1")
        Long id;
        @Schema(description = "제목", example = ExampleValue.Course.TITLE)
        String title;
        @Schema(description = "설명", example = ExampleValue.Course.DESCRIPTION)
        String description;
        @Schema(description = "강사", example = ExampleValue.Member.NAME)
        String instructor;
        @Schema(description = "카테고리", example = ExampleValue.Course.CATEGORY)
        String category;
        @Schema(description = "샘플 강의")
        SampleDto sample;
        @Schema(description = "썸네일 이미지")
        ImageInfo thumbnailImage;

        @Schema(description = "소개 이미지 목록")
        List<ImageInfo> descriptionImages;
        @Schema(description = "평점", example = "3.5")
        double score;
        @Schema(description = "찜", example = "1500")
        long wish;

        public Response(Course course, Unit sampleUnit, double averageScore) {
            this.id = course.getId();
            this.title = course.getTitle();
            this.description = course.getDescription();
            this.instructor = course.getInstructorName();
            this.category = course.getCategory().getTitle();
            if (sampleUnit != null){
                this.sample = new SampleDto(sampleUnit);
            }
            this.thumbnailImage = course.getThumbnailImage();
            this.descriptionImages = course.getDescriptionImages();
            this.score = averageScore;
            this.wish = course.getWishList().size();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class StatusResponse {
        @Schema(description = "아이디넘버")
        Long courseId;
        @Schema(description = "제목", example = ExampleValue.Course.TITLE)
        String title;
        @Schema(description = "강사", example = ExampleValue.Member.NAME)
        String instructor;
        @Schema(description = "카테고리", example = ExampleValue.Course.CATEGORY)
        String category;
        @Schema(description = "완료한 강의 수")
        int completedUnits;
        @Schema(description = "전체 강의 수")
        int entireUnits;
        @Schema(description = "다음에 시청할 강의 정보")
        NextUnitInfo nextUnitInfo;
        @Schema(description = "썸네일 이미지")
        ImageInfo thumbnailImage;

        public StatusResponse(Course course, int completedUnits, NextUnitInfo nextUnitInfo){
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.instructor = course.getInstructorName();
            this.category = course.getCategory().getTitle();
            this.thumbnailImage = course.getThumbnailImage();
            this.entireUnits = course.getUnitList().size();
            this.completedUnits = completedUnits;
            this.nextUnitInfo = nextUnitInfo;
        }
    }
}
