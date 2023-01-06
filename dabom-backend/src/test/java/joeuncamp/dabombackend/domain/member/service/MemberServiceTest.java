package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberManageService memberManageService;

    @Test
    @DisplayName("일반 테스트 - 회원을 등록하고 조회한다.")
    void 일반_테스트_회원을_등록하고_조회한다() {
        // given
        MemberCreationRequestDto memberCreationRequestDto = new MemberCreationRequestDto(
                ExampleValue.Member.ACCOUNT,
                ExampleValue.Member.PASSWORD,
                ExampleValue.Member.NICKNAME,
                ExampleValue.Member.MOBILE,
                ExampleValue.Member.BIRTH_DATE
        );

        // when
        Long createdId = memberManageService.createMember(memberCreationRequestDto);
        Member foundMember = memberManageService.getMember(createdId);

        // then
        assertThat(memberCreationRequestDto.getAccount()).isEqualTo(foundMember.getAccount());
        System.out.println("foundMember = " + foundMember.getId());
    }
}
