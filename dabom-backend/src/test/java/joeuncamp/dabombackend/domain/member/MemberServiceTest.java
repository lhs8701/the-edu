package joeuncamp.dabombackend.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTest {
    @Test
    @DisplayName("회원을 등록하고 조회한다.")
    void test() {
        // given
        MemberManageService memberManageService = new MemberManageService();
        MemberCreationRequestDto personalData = new MemberCreationRequestDto("test");

        // when
        Long createdId = memberManageService.createMember(personalData);
        Member foundMember = memberManageService.getMember(createdId);

        // then
        assertThat(personalData.getAccount()).isEqualTo(foundMember.getAccount());
    }
}
