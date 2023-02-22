package joeuncamp.dabombackend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.constant.StaticFilePath;
import joeuncamp.dabombackend.global.constant.ValidationMessage;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Collections;

@Getter
@NoArgsConstructor
public class AppleAuthDto {

    @Getter
    public static class LoginRequest{
        @Schema(description = "각 소셜 사이트에서 발급받은 토큰")
        String socialToken;
    }

    @Getter
    public static class SignupRequest{
        @Schema(description = "각 소셜 사이트에서 발급받은 토큰")
        String socialToken;

        @Email(message = ValidationMessage.NOT_VALID_EMAIL)
        @Schema(description = "이메일", example = ExampleValue.Member.ACCOUNT)
        String email;

        @Length(min = 2, max = 16, message = ValidationMessage.NOT_VALID_NICKNAME)
        @Schema(description = "닉네임", example = ExampleValue.Member.NICKNAME)
        String nickname;

        public Member toEntity() {
            return Member.builder()
                    .nickname(this.nickname)
                    .email(this.email)
                    .loginType(LoginType.APPLE)
                    .profileImage(new ImageInfo(StaticFilePath.DEFAULT_PROFILE_IMAGE.getUrl()))
                    .socialId(this.socialToken)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .payPoint(0)
                    .emailCertified(true)
                    .certified(false)
                    .locked(false)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UnlinkRequest {
        @Schema(hidden = true)
        String accessToken;
        @Schema(hidden = true)
        String refreshToken;
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
}
