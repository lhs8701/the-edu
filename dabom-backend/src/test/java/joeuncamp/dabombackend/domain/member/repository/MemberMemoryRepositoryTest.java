package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
@Transactional
public class MemberMemoryRepositoryTest {

    @Test
    @DisplayName("메모리 DB로 회원을 저장하고 조회한다.")
    void 메모리_DB로_회원을_등록하고_조회한다() {
        // given
        MemberMemoryRepository memberRepository = new MemberMemoryRepository();
        MemberCreationRequestDto memberCreationRequestDto = new MemberCreationRequestDto("test", "test", "010-1234-5678", "test", "test");

        // when
        Long savedId = memberRepository.save(memberCreationRequestDto.toEntity());
        Member foundMember = memberRepository.findById(savedId);

        // then
        assertThat(memberCreationRequestDto.getAccount()).isEqualTo(foundMember.getAccount());
    }

    @Test
    @DisplayName("메모리 DB로 저장되지 않은 회원을 조회할 경우 예외가 발생한다.")
    void 저장되지_않은_회원을_조회할_경우_예외가_발생한다() {
        // given
        MemberMemoryRepository memberRepository = new MemberMemoryRepository();

        // when

        // then
        assertThatThrownBy(() -> memberRepository.findById(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
