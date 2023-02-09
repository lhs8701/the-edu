package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.CreatorRequestDto;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAlreadyCreatorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @DisplayName("회원에게 크리에이터 계정이 있으면 true를 반환한다.")
    void 크리에이터_계정이_있으면_true를_반환한다() {
        // given
        Long memberId = 1L;
        CreatorProfile creatorProfile = CreatorProfile.builder().build();
        Member member = Member.builder()
                .id(memberId)
                .creatorProfile(creatorProfile)
                .build();

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
