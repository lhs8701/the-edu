package joeuncamp.dabombackend.domain.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.event.entity.Event;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

        public Event toEntity(Member member) {
            return Event.builder()
                    .title(title)
                    .content(content)
                    .bannerImage(new ImageInfo(bannerImage))
                    .writer(member)
                    .build();
        }
    }
}
