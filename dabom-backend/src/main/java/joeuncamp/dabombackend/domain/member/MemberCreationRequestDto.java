package joeuncamp.dabombackend.domain.member;

import lombok.Getter;

@Getter
public class MemberCreationRequestDto {
    String account;
    String password;
    String nickname;
    String mobile;
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
