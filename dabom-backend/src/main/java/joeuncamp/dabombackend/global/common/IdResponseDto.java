package joeuncamp.dabombackend.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class IdResponseDto {
    @Schema(description = "아이디넘버", example = "1")
    Long id;
}
