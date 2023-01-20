package joeuncamp.dabombackend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialTokenRequestDto {
    @Schema(description = "각 소셜 사이트에서 발급받은 토큰", example = "sdkjfxkjlsdjfkekjfls")
    String socialToken;
}
