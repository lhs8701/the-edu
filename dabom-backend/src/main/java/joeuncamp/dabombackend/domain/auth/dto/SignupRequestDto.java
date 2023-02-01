package joeuncamp.dabombackend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.constant.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.Collections;

@Getter
@AllArgsConstructor
@Builder
public class SignupRequestDto {
    @Email(message = ValidationMessage.NOT_VALID_EMAIL)
    @Schema(description = "계정", example = ExampleValue.Member.ACCOUNT)
    String account;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$" , message = ValidationMessage.NOT_VALID_PASSWORD)
    @Schema(description = "비밀번호", example = ExampleValue.Member.PASSWORD)
    String password;

    @Length(min=2, max = 16, message = ValidationMessage.NOT_VALID_NICKNAME)
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
                .profileImage(new ImageInfo(ExampleValue.Member.PROFILE_IMAGE))
                .loginType(LoginType.BASIC)
                .socialId(null)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
