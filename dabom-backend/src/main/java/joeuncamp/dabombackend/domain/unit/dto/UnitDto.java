package joeuncamp.dabombackend.domain.unit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.file.FileUtil;
import joeuncamp.dabombackend.domain.file.video.entity.VideoInfo;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.*;


public class UnitDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class UploadRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long courseId;
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
    @Getter
    @AllArgsConstructor
    public static class PlayRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long unitId;
    }

    public static class Response{
        @Schema(description = "강의 아이디넘버", example = "1")
        Long unitId;
        @Schema(description = "순서", example = "1")
        int sequence;
        @Schema(description = "챕터 아이디넘버", example = "1")
        Long chapterId;
        @Schema(description = "강의 제목", example = ExampleValue.Unit.TITLE)
        String title;
        @Schema(description = "강의 설명", example = ExampleValue.Unit.DESCRIPTION)
        String description;
        @Schema(description = "비디오 정보")
        VideoInfo videoInfo;

        public Response(Unit unit){
            this.unitId = unit.getId();
            this.sequence = unit.getSequence();
            this.chapterId = unit.getChapterId();
            this.title = unit.getTitle();
            this.description = unit.getDescription();
            this.videoInfo = unit.getVideoInfo();
        }
    }
}
