package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.domain.member.dto.ProfileResponseDto;
import joeuncamp.dabombackend.domain.member.dto.ProfileUpdateParam;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MemberProfileTest {
    @InjectMocks
    MemberService memberService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("나의 프로필을 조회한다.")
    void 나의_프로필을_조회한다() {
        // given
        Member member = MemberCreationRequestDto.builder()
                .account(ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .mobile(ExampleValue.Member.MOBILE)
                .birthDate(ExampleValue.Member.BIRTH_DATE)
                .build().toEntity();

        given(memberJpaRepository.findById(any())).willReturn(Optional.of(member));

        // when
        ProfileResponseDto profile = memberService.getMyProfile(1L);

        // then
        Assertions.assertThat(profile.getAccount()).isEqualTo(member.getAccount());
    }

    @Test
    @DisplayName("나의 프로필을 수정한다.")
    void 나의_프로필을_수정한다() {
        // given
        Member member = MemberCreationRequestDto.builder()
                .account(ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .mobile(ExampleValue.Member.MOBILE)
                .birthDate(ExampleValue.Member.BIRTH_DATE)
                .build().toEntity();

        given(memberJpaRepository.findById(any())).willReturn(Optional.of(member));

        // when
        ProfileUpdateParam updateParam = ProfileUpdateParam.builder()
                .nickname("updated")
                .email("updated")
                .build();
        memberService.updateMyProfile(updateParam, 1L);

        // then
        Assertions.assertThat(member.getNickname()).isEqualTo("updated");
    }
}
