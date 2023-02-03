package joeuncamp.dabombackend.domain.player.record.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RecordDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SaveRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long unitId;
        @Schema(description = "마지막 시청 시간", example = "15.6")
        Double time;
    }

    @Getter
    @AllArgsConstructor
    public static class GetRequest {
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long unitId;
    }
}
