package joeuncamp.dabombackend.domain.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Member {
    @Schema(description = "아이디", example = "1")
    Long id;
    @Schema(description = "계정", example = "abc1234")
    String account;
    @Schema(description = "비밀번호", example = "qwer1234")
    String password;
    @Schema(description = "닉네임", example = "헬로")
    String nickname;
    @Schema(description = "전화번호", example = "010-1234-5678")
    String mobile;
    @Schema(description = "권한", example = "일반")
    String role;
}
