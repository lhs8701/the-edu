package joeuncamp.dabombackend.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {

    @Test
    @DisplayName("회원을 생성하고, DB에 등록한다.")
    void test() {
        // given
        MemberService memberService = new MemberService();
        MemberCreationRequestDto personalData = new MemberCreationRequestDto("test");

        // when
        Member createdMember = MemberService.createMember(personalData);

        // then
        assertThat(createdMember.getAccount()).isEqualTo(personalData.getAccount());
    }
}
