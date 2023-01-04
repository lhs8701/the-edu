package joeuncamp.dabombackend.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest {

    @Test
    @DisplayName("메모리 DB로 회원을 저장하고 조회한다.")
    void 메모리_DB로_회원을_등록하고_조회한다() {
        // given
        MemberRepository memberRepository = new MemberMemoryRepository();
        MemberCreationRequestDto personalData = new MemberCreationRequestDto("test");

        // when
        Long savedId = memberRepository.save(personalData.toEntity());
        Member foundMember = memberRepository.findById(savedId);

        // then
        assertThat(personalData.getAccount()).isEqualTo(foundMember.getAccount());
    }
}
