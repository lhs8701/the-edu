package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
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
    @DisplayName("일반 테스트 - 회원을 등록하고 조회한다.")
    void 일반_테스트_회원을_등록하고_조회한다() {
        // given
        MemberCreationRequestDto personalData = new MemberCreationRequestDto("test");

        // when
        Long createdId = memberManageService.createMember(personalData);
        Member foundMember = memberManageService.getMember(createdId);

        // then
        assertThat(personalData.getAccount()).isEqualTo(foundMember.getAccount());
    }
}
