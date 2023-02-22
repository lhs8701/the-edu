package joeuncamp.dabombackend.domain.course.dto;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.global.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class CreatorStatusDto {
    List<CourseStatus> courseStatus;
    Long totalProfit;

    @Getter
    @AllArgsConstructor
    public static class CourseStatus {
        Long courseId;
        String title;
        CategoryType category;
        ImageInfo thumbnailImage;
        List<Map.Entry<Integer, List<Long>>> monthlyProfit;
        Long cancelCount;
        Long studentCount;
        Long numOfCompleted;
        Double averageScore;

        @Builder
        public CourseStatus(Course course, List<Map.Entry<Integer, List<Long>>> monthlyProfit, Long cancelCount, Long studentCount, Long numOfCompleted, Double averageScore) {
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.category = course.getCategory();
            this.thumbnailImage = course.getThumbnailImage();
            this.monthlyProfit = monthlyProfit;
            this.cancelCount = cancelCount;
            this.studentCount = studentCount;
            this.numOfCompleted = numOfCompleted;
            this.averageScore = averageScore;
        }
    }
}