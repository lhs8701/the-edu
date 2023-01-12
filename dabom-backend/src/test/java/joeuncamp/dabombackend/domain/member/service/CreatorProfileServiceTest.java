package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.CreatorRequestDto;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CreatorProfileServiceTest {

    @InjectMocks
    CreatorService creatorService;

    @Mock
    CreatorProfileJpaRepository creatorProfileJpaRepository;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("회원의 크리에이터 프로필을 생성한다.")
    void 크리에이터_계정을_활성화한다(){
        //given
        Long memberId = 1L;
        Member member = Member.builder()
                .id(memberId)
                .build();
        CreatorRequestDto dto = CreatorRequestDto.builder()
                .creatorNickname(ExampleValue.CreatorProfile.CREATOR_NICKNAME)
                .build();
        given(memberJpaRepository.findById(memberId)).willReturn(Optional.of(member));
        given(creatorProfileJpaRepository.save(any())).willReturn(new CreatorProfile());

        //when
        creatorService.activateCreatorProfile(memberId, dto);

        //then
        assertThat(member.getCreatorProfile()).isNotNull();
    }

    @Test
    @DisplayName("회원에게 크리에이터 계정이 있으면 true를 반환한다.")
    void 크리에이터_계정이_있으면_true를_반환한다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        given(memberJpaRepository.findById(1L)).willReturn(Optional.of(member));
        given(creatorProfileJpaRepository.save(any())).willReturn(new CreatorProfile());
        CreatorRequestDto dto = CreatorRequestDto.builder().build();
        creatorService.activateCreatorProfile(1L, dto);

        // when
        boolean result = creatorService.hasCreatorProfile(member);

        // then
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("회원에게 크리에이터 계정이 없으면 false를 반환한다.")
    void 크리에이터_계정이_없으면_false를_반환한다() {
        // given
        Member member = Member.builder().build();

        // when
        boolean result = creatorService.hasCreatorProfile(member);

        // then
        assertThat(result).isEqualTo(false);
    }
}
