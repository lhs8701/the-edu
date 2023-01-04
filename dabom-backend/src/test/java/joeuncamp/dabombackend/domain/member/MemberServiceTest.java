package joeuncamp.dabombackend.domain.member;

import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.domain.member.service.MemberManageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberManageService memberManageService;

    @Test
    @DisplayName("회원을 등록하고 조회한다.")
    void test() {
        // given
        MemberCreationRequestDto personalData = new MemberCreationRequestDto("test");

        // when
        Long createdId = memberManageService.createMember(personalData);
        Member foundMember = memberManageService.getMember(createdId);

        // then
        assertThat(personalData.getAccount()).isEqualTo(foundMember.getAccount());
    }
}
