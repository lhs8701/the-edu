package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.CreatorRequestDto;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CreatorProfileServiceTest {

    @InjectMocks
    CreatorService creatorService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Mock
    CreatorProfileJpaRepository creatorProfileJpaRepository;

    @Test
    @DisplayName("회원의 크리에이터 프로필을 생성한다.")
    void 크리에이터_계정을_활성화한다(){
        //given
        Member member = Member.builder().build();
        CreatorRequestDto dto = CreatorRequestDto.builder().build();

        //when
        creatorService.activateCreatorProfile(member, dto);
        CreatorProfile creatorProfile = member.getCreatorProfile();

        //then
        assertThat(creatorProfile).isNotNull();
    }

    @Test
    @DisplayName("회원에게 크리에이터 계정이 있으면 true를 반환한다.")
    void 크리에이터_계정이_있으면_true를_반환한다() {
        // given
        Member member = Member.builder().build();
        CreatorRequestDto dto = CreatorRequestDto.builder().build();
        creatorService.activateCreatorProfile(member, dto);

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
        assertThat(result).isEqualTo(true);
    }
}
