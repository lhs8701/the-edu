package joeuncamp.dabombackend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReissueRequestDto {
    @Schema(description = "어세스 토큰", example = "abcedfg12345678")
    @NotBlank
    String accessToken;

    @Schema(description = "리프레시 토큰", example = "abcedfg12345678")
    @NotBlank
    String refreshToken;
}
