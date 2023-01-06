package joeuncamp.dabombackend.domain.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
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

    @Schema(description = "계정", example = "abc1234@naver.com")
    String account;

    @Schema(description = "비밀번호", example = "qwer1234")
    String password;

    @Schema(description = "닉네임", example = "헬로")
    String nickname;

    @Schema(description = "전화번호", example = "010-1234-5678")
    String mobile;

    @Schema(description = "생년월일", example= "2002.11.26")
    String birthDate;

    @Schema(description = "이메일", example = "abc1234@naver.com")
    String email;

    @Schema(description = "로그인유형", example = "normal/kakao/naver/apple")
    String loginType;

    @Schema(description = "로그인토큰", example = "uuid")
    String loginToken;

    @Schema(description = "권한", example = "일반")
    String role;
}
