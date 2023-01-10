package joeuncamp.dabombackend.domain.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.Collections;

@Getter
@AllArgsConstructor
@Builder
public class SignUpRequestDto {
    @Email(message = ValidationMessage.NOT_VALID_EMAIL)
    @Schema(description = "계정", example = ExampleValue.Member.ACCOUNT)
    String account;
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$" , message = ValidationMessage.NOT_VALID_PASSWORD)
    @Schema(description = "비밀번호", example = ExampleValue.Member.PASSWORD)
    String password;
    @Length(min=2, max = 16, message = ValidationMessage.NOT_VALID_NICKNAME)
    @Schema(description = "닉네임", example = ExampleValue.Member.NICKNAME)
    String nickname;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = ValidationMessage.NOT_VALID_MOBILE)
    @Schema(description = "전화번호", example = ExampleValue.Member.MOBILE)
    String mobile;

    @Pattern(regexp = "^(19|20)\\d{2}.(0[1-9]|1[012]).(0[1-9]|[12][0-9]|3[0-1])$", message = ValidationMessage.NOT_VALID_BIRTH_DATE)
    @Schema(description = "생년월일", example = ExampleValue.Member.BIRTH_DATE)
    String birthDate;


    /**
     * DTO 내용을 바탕으로 Member 엔티티 생성
     *
     * @return 생성된 Member 엔티티
     */
    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .account(account)
                .password(encodedPassword)
                .nickname(nickname)
                .mobile(mobile)
                .birthDate(birthDate)
                .email(account)
                .loginToken("normal")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
