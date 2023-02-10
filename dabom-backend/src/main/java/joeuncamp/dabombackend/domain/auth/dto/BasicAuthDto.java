package joeuncamp.dabombackend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.constant.StaticFilePath;
import joeuncamp.dabombackend.global.constant.ValidationMessage;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Collections;

public class BasicAuthDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignupRequest{
        @Email(message = ValidationMessage.NOT_VALID_EMAIL)
        @Schema(description = "계정", example = ExampleValue.Member.ACCOUNT)
        String account;

//        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@!%*#?&]{8,16}$", message = ValidationMessage.NOT_VALID_PASSWORD)
        @Schema(description = "비밀번호", example = ExampleValue.Member.PASSWORD)
        String password;

        @Length(min = 2, max = 16, message = ValidationMessage.NOT_VALID_NICKNAME)
        @Schema(description = "닉네임", example = ExampleValue.Member.NICKNAME)
        String nickname;

        /**
         * DTO 내용을 바탕으로 Member 엔티티 생성
         *
         * @return 생성된 Member 엔티티
         */
        public Member toEntity(String encodedPassword) {
            return Member.builder()
                    .account(this.account)
                    .password(encodedPassword)
                    .nickname(this.nickname)
                    .email(this.account)
                    .loginType(LoginType.BASIC)
                    .profileImage(new ImageInfo(StaticFilePath.DEFAULT_PROFILE_IMAGE.getUrl()))
                    .socialId(null)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {
        @Email(message = ValidationMessage.NOT_VALID_EMAIL)
        @Schema(description = "계정", example = ExampleValue.Member.ACCOUNT)
        String account;
        //    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$" , message = ValidationMessage.NOT_VALID_PASSWORD)
        @Schema(description = "비밀번호", example = ExampleValue.Member.PASSWORD)
        String password;
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
    @AllArgsConstructor
    public static class UnlinkRequestDto {
        @NotBlank
        String accessToken;
        @NotBlank
        String refreshToken;
    }

    @Getter
    @NoArgsConstructor
    public static class ReissueRequest {
        @Schema(description = "어세스 토큰", example = ExampleValue.JWT.ACCESS)
        @NotBlank
        String accessToken;

        @Schema(description = "리프레시 토큰", example = ExampleValue.JWT.REFRESH)
        @NotBlank
        String refreshToken;
    }
}
