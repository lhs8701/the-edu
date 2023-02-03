package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MyCourseDto {

    @Getter
    @AllArgsConstructor
    public static class Request {
        @Schema(hidden = true)
        Long memberId;
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
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
        @Schema(description = "완료한 강의 수")
        int completedNumber;

        public Response(Course course, int completedNumber){
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.instructor = course.getInstructorName();
            this.category = course.getCategory().getTitle();
            this.thumbnailImage = course.getThumbnailImage();
            this.completedNumber = completedNumber;
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ShortResponse{
        @Schema(description = "아이디넘버", example = "1")
        Long courseId;
        @Schema(description = "제목", example = ExampleValue.Course.TITLE)
        String title;
        @Schema(description = "강사", example = ExampleValue.Member.NAME)
        String instructor;

        public ShortResponse(Course course){
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.instructor = course.getInstructorName();
        }
    }
}