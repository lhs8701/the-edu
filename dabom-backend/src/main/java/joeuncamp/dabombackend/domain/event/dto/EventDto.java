package joeuncamp.dabombackend.domain.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.event.entity.Event;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EventDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CreateRequest {
        @Schema(hidden = true)
        Long memberId;
        @Schema(description = "제목", example = ExampleValue.Event.TITLE)
        String title;
        @Schema(description = "내용", example = ExampleValue.Event.CONTENT)
        String content;
        @Schema(description = "배너 이미지", example = ExampleValue.Image.THUMBNAIL)
        String bannerImage;
        @Schema(description = "시작 일자")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate;
        @Schema(description = "종료 일자")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate;

        public Event toEntity(Member member) {
            return Event.builder()
                    .title(title)
                    .content(content)
                    .bannerImage(new ImageInfo(bannerImage))
                    .writer(member)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
        }
    }

    @Getter
    public static class ShortResponse {
        @Schema(description = "아이디넘버")
        Long id;
        @Schema(description = "제목", example = ExampleValue.Event.TITLE)
        String title;
        @Schema(description = "디데이")
        long dDay;
        @Schema(description = "시작 일자")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate startDate;
        @Schema(description = "종료 일자")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate endDate;
        @Schema(description = "배너 이미지", example = ExampleValue.Image.THUMBNAIL)
        ImageInfo bannerImage;

        public ShortResponse(Event event){
            this.id = event.getId();
            this.title = event.getTitle();
            this.dDay = ChronoUnit.DAYS.between(LocalDate.now(), event.getEndDate());
            this.startDate = event.getStartDate();
            this.endDate = event.getEndDate();
            this.bannerImage = event.getBannerImage();
        }
    }

    @Getter
    public static class Response {
        @Schema(description = "아이디넘버")
        Long id;
        @Schema(description = "제목", example = ExampleValue.Event.TITLE)
        String title;
        @Schema(description = "내용", example = ExampleValue.Event.CONTENT)
        String content;
        @Schema(description = "작성자", example = ExampleValue.Member.NICKNAME)
        String writer;
        @Schema(description = "디데이")
        long dDay;
        @Schema(description = "시작 일자")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate startDate;
        @Schema(description = "종료 일자")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate endDate;
        @Schema(description = "배너 이미지", example = ExampleValue.Image.THUMBNAIL)
        ImageInfo bannerImage;

        public Response(Event event){
            this.id = event.getId();
            this.title = event.getTitle();
            this.content = event.getContent();
            this.writer = event.getWriter().getNickname();
            this.dDay = ChronoUnit.DAYS.between(LocalDate.now(), event.getEndDate());
            this.startDate = event.getStartDate();
            this.endDate = event.getEndDate();
            this.bannerImage = event.getBannerImage();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateRequest {
        @Schema(hidden = true)
        Long eventId;
        @Schema(description = "제목", example = ExampleValue.Event.TITLE)
        String title;
        @Schema(description = "내용", example = ExampleValue.Event.CONTENT)
        String content;
        @Schema(description = "배너 이미지", example = ExampleValue.Image.THUMBNAIL)
        String bannerImage;
        @Schema(description = "시작 일자")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate;
        @Schema(description = "종료 일자")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate;
    }
}
