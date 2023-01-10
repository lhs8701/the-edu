package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("일반 테스트 - 회원을 등록하고 조회한다.")
    void 일반_테스트_회원을_등록하고_조회한다() {
        // given
        MemberCreationRequestDto memberCreationRequestDto = MemberCreationRequestDto.builder()
                .account(ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .mobile(ExampleValue.Member.MOBILE)
                .birthDate(ExampleValue.Member.BIRTH_DATE)
                .build();

        // when
        Long createdId = memberService.createMember(memberCreationRequestDto);
        Member foundMember = memberService.getMember(createdId);

        // then
        assertThat(memberCreationRequestDto.getAccount()).isEqualTo(foundMember.getAccount());
        System.out.println("foundMember = " + foundMember.getId());
    }
}
