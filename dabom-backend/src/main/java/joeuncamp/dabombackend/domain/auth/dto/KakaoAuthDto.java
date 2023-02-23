package joeuncamp.dabombackend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class KakaoAuthDto {

    @Getter
    @NoArgsConstructor
    public static class LoginRequest{
        @Schema(description = "각 소셜 사이트에서 발급받은 토큰")
        @NotBlank
        String socialToken;
    }

    @Getter
    public static class LoginResponse {
        Long memberId;
        Long creatorId;
        TokenForm tokenForm;
        public LoginResponse(Member member, TokenForm tokenForm){
            this.memberId = member.getId();
            this.creatorId = -1L;
            this.tokenForm = tokenForm;
            if (member.isCreator()){
                this.creatorId = member.getCreatorProfile().getId();
            }
        }
    }
    @Getter
    @Setter
    public static class UnlinkRequest {
        @Schema(description = "각 소셜 사이트에서 발급받은 토큰")
        @NotBlank
        String socialToken;
        @Schema(hidden = true, description = "어세스토큰")
        String accessToken;
        @Schema(hidden = true, description = "리프레시토큰")
        String refreshToken;
    }
}
