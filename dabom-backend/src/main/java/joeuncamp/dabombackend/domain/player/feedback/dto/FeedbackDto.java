package joeuncamp.dabombackend.domain.player.feedback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.feedback.entity.Feedback;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FeedbackDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateRequest {
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long unitId;
        @Schema(description = "좋아요", example = "false")
        Boolean thumbsUp;
        @Schema(description = "싫어요", example = "false")
        Boolean thumbsDown;
    }
    @Getter
    @AllArgsConstructor
    public static class GetRequest {
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long unitId;
    }

    @Getter
    public static class Response{
        @Schema(description = "좋아요", example = "false")
        boolean thumbsUp;
        @Schema(description = "싫어요", example = "false")
        boolean thumbsDown;

        public Response(Feedback feedback){
            this.thumbsUp = feedback.isThumbsUp();
            this.thumbsDown = feedback.isThumbsDown();
        }
    }


}
