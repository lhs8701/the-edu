package joeuncamp.dabombackend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseDto {
    @Schema(name = "아이디넘버", example = "1")
    Long id;
    @Schema(name="계정", example = ExampleValue.Member.ACCOUNT)
    String account;
    @Schema(name="별명", example = ExampleValue.Member.NICKNAME)
    String nickname;
    @Schema(name="전화번호", example = ExampleValue.Member.MOBILE)
    String mobile;
    @Schema(name = "생년월일", example = ExampleValue.Member.BIRTH_DATE)
    String birthDate;
    @Schema(name = "이메일", example = ExampleValue.Member.EMAIL)
    String email;
    @Schema(name = "로그인 유형", example = "basic")
    LoginType loginType;

    public ProfileResponseDto(Member member) {
        this.id = member.getId();
        this.account = member.getAccount();
        this.nickname = member.getNickname();
        this.mobile = member.getMobile();
        this.birthDate = member.getBirthDate();
        this.email = member.getEmail();
        this.loginType = member.getLoginType();
    }
}
