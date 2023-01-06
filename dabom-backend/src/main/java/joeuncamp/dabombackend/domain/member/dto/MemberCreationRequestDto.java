package joeuncamp.dabombackend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.global.constant.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springdoc.api.ErrorMessage;

@Getter
@AllArgsConstructor
public class MemberCreationRequestDto {
    @Email(message = ValidationMessage.NOT_VALID_EMAIL)
    @Schema(description = "계정", example = "abc1234")
    String account;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,}$" , message = ValidationMessage.NOT_VALID_PASSWORD)
    @Schema(description = "비밀번호", example = "qwer1234")
    String password;
    @Length(min=2, max = 16, message = ValidationMessage.NOT_VALID_NICKNAME)
    @Schema(description = "닉네임", example = "헬로")
    String nickname;
    @Schema(description = "전화번호", example = "010-1234-5678")
    String mobile;

    @Pattern(regexp = "^(19|20)\\d{2}.(0[1-9]|1[012]).(0[1-9]|[12][0-9]|3[0-1])$", message = ValidationMessage.NOT_VALID_BIRTH_DATE)
    @Schema(description = "생년월일", example = "2002.11.26")
    String birthDate;


    /**
     * DTO 내용을 바탕으로 Member 엔티티 생성
     *
     * @return 생성된 Member 엔티티
     */
    public Member toEntity() {
        return Member.builder()
                .account(account)
                .password(password)
                .nickname(nickname)
                .mobile(mobile)
                .birthDate(birthDate)
                .email(account)
                .loginToken("normal")
                .role("general")
                .build();
    }
}
