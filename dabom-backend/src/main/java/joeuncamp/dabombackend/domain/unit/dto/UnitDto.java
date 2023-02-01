package joeuncamp.dabombackend.domain.unit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.file.FileUtil;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class UnitDto {
    Long memberId;
    Long courseId;
    UploadRequest uploadRequest;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UploadRequest{
        @Schema(description = "강의 제목", example = ExampleValue.Unit.TITLE)
        String title;
        @Schema(description = "강의 설명", example = ExampleValue.Unit.DESCRIPTION)
        String description;
        @Schema(description = "비디오 경로", example = ExampleValue.Unit.VIDEO_URL)
        String videoUrl;

        public Unit toEntity(Course course){
            return Unit.builder()
                    .title(title)
                    .description(description)
                    .videoInfo(FileUtil.getVideoInfo(videoUrl))
                    .course(course)
                    .build();
        }
    }
}
