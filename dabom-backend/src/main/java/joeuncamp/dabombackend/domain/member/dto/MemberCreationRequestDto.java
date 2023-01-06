package joeuncamp.dabombackend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.global.message.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreationRequestDto {
    @Schema(description = "계정", example = "abc1234")
    @Email(message = ValidationMessage.NOT_VALID_EMAIL)
    String account;
    @Schema(description = "비밀번호", example = "qwer1234")
    String password;
    @Schema(description = "닉네임", example = "헬로")
    String nickname;
    @Schema(description = "전화번호", example = "010-1234-5678")
    String mobile;
    @Schema(description = "권한", example = "일반")
    String role;

    /**
     * 테스트용 DTO 생성자
     *
     * @param test 오버로드용 임시 필드
     */
    public MemberCreationRequestDto(String test) {
        this.account = "test_account";
        this.password = "test_password";
        this.nickname = "test_nickname";
        this.mobile = "010-1111-1564";
        this.role = "normal";
    }


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
                .role(role)
                .build();
    }
}
