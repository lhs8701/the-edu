package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.ProfileDto;
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
public class MemberServiceTest {
    @InjectMocks
    MemberService memberService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("나의 프로필을 조회한다.")
    void 나의_프로필을_조회한다() {
        // given
        Member member = Member.builder()
                .nickname(ExampleValue.Member.NICKNAME)
                .build();

        given(memberJpaRepository.findById(any())).willReturn(Optional.of(member));

        // when
        ProfileDto.Response profile = memberService.getMyProfile(1L);

        // then
        Assertions.assertThat(profile.getAccount()).isEqualTo(member.getAccount());
    }

    @Test
    @DisplayName("나의 프로필을 수정한다.")
    void 나의_프로필을_수정한다() {
        // given
        Member member = Member.builder()
                .nickname(ExampleValue.Member.NICKNAME)
                .build();

        given(memberJpaRepository.findById(any())).willReturn(Optional.of(member));

        // when
        ProfileDto.UpdateRequest updateParam = ProfileDto.UpdateRequest.builder()
                .memberId(1L)
                .nickname("updated")
                .email("updated")
                .build();
        memberService.updateMyProfile(updateParam);

        // then
        Assertions.assertThat(member.getNickname()).isEqualTo("updated");
    }

    @Test
    @DisplayName("비어있는 필드를 제외하고 수정한다.")
    void 비어있는_필드를_제외하고_수정한다() {
        // given
        Member member = Member.builder()
                .nickname(ExampleValue.Member.NICKNAME)
                .email(ExampleValue.Member.EMAIL)
                .build();

        given(memberJpaRepository.findById(any())).willReturn(Optional.of(member));

        // when
        ProfileDto.UpdateRequest updateParam = ProfileDto.UpdateRequest.builder()
                .nickname("updated")
                .build();

        memberService.updateMyProfile(updateParam);

        // then
        Assertions.assertThat(member.getNickname()).isEqualTo("updated");
        Assertions.assertThat(member.getEmail()).isEqualTo(ExampleValue.Member.EMAIL);
    }
}
