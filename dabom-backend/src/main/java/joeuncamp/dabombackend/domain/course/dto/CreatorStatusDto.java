package joeuncamp.dabombackend.domain.course.dto;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.global.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreatorStatusDto {
    List<CourseStatus> courseStatus;
    long totalProfit;

    @Getter
    @AllArgsConstructor
    public static class CourseStatus {
        Long courseId;
        String title;
        CategoryType category;
        ImageInfo thumbnailImage;
        Long profit;
        Long cancelCount;
        Long studentCount;
        Long numOfCompleted;
        Double averageScore;

        @Builder
        public CourseStatus(Course course, Long profit, Long cancelCount, Long studentCount, Long numOfCompleted, Double averageScore) {
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.category = course.getCategory();
            this.thumbnailImage = course.getThumbnailImage();
            this.profit = profit;
            this.cancelCount = cancelCount;
            this.studentCount = studentCount;
            this.numOfCompleted = numOfCompleted;
            this.averageScore = averageScore;
        }
    }
}