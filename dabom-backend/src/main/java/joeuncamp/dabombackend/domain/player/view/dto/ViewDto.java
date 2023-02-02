package joeuncamp.dabombackend.domain.player.view.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ViewDto {

    public static class SaveRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long unitId;
        @Schema(description = "이전에 시청중인 강의 아이디넘버", example = "1")
        String prevUnitId;
        @Schema(description = "마지막 시청 시간", example = "15.6")
        Double time;
        @Schema(description = "완강 여부", example = "true")
        Boolean completed;
    }
}
