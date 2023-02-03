package joeuncamp.dabombackend.domain.player.record.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ViewDto {

    @Getter
    @AllArgsConstructor
    public static class CompleteRequest {
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long unitId;
    }
}
