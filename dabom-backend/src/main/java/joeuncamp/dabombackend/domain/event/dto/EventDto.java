package joeuncamp.dabombackend.domain.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.event.entity.Event;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        @Schema(description = "배너 이미지", example = ExampleValue.Image.URL)
        String bannerImage;
        @Schema(description = "종료 일자")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate;

        public Event toEntity(Member member) {
            return Event.builder()
                    .title(title)
                    .content(content)
                    .bannerImage(new ImageInfo(bannerImage))
                    .writer(member)
                    .endDate(endDate)
                    .build();
        }
    }

    @Getter
    public static class Response {
        @Schema(description = "제목", example = ExampleValue.Event.TITLE)
        String title;
        @Schema(description = "내용", example = ExampleValue.Event.CONTENT)
        String content;
        @Schema(description = "작성자", example = ExampleValue.Member.NICKNAME)
        String writer;
        @Schema(description = "종료 일자")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate endDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "생성 시간", example = ExampleValue.Time.DATE)
        LocalDateTime createdTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "최근 수정 시간", example = ExampleValue.Time.DATE)
        LocalDateTime modifiedTime;
        @Schema(description = "배너 이미지", example = ExampleValue.Image.URL)
        ImageInfo bannerImage;

        public Response(Event event){
            this.title = event.getTitle();
            this.content = event.getContent();
            this.writer = event.getWriter().getNickname();
            this.endDate = event.getEndDate();
            this.createdTime = event.getCreatedTime();
            this.modifiedTime = event.getModifiedTime();
            this.bannerImage = event.getBannerImage();
        }
    }
}
