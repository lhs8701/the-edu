package joeuncamp.dabombackend.domain.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "아이디", example = "1")
    Long id;

    @Schema(description = "계정", example = ExampleValue.Member.ACCOUNT)
    String account;

    @Schema(description = "비밀번호", example = ExampleValue.Member.PASSWORD)
    String password;

    @Schema(description = "닉네임", example = ExampleValue.Member.NICKNAME)
    String nickname;

    @Schema(description = "전화번호", example = ExampleValue.Member.MOBILE)
    String mobile;

    @Schema(description = "생년월일", example= ExampleValue.Member.BIRTH_DATE)
    String birthDate;

    @Schema(description = "이메일", example = ExampleValue.Member.EMAIL)
    String email;

    @Schema(description = "로그인유형", example = "normal/kakao/naver/apple")
    String loginType;

    @Schema(description = "로그인토큰", example = "uuid")
    String loginToken;

    @Schema(description = "권한", example = "일반")
    String role;
}
