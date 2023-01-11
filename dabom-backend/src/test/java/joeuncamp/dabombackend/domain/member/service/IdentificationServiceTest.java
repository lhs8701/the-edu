package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.entity.Creator;
import joeuncamp.dabombackend.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class IdentificationServiceTest {

    @InjectMocks
    IdentificationService identificationService;

    @Test
    @DisplayName("일반회원을 구분한다.")
    void 일반회원을_구분한다() {
        // given
        Member member = Member.builder().build();

        // when
        boolean result = identificationService.isCreator(member);

        // then
        assertThat(result).isEqualTo(false);
    }

    @Test
    @DisplayName("크리에이터를 구분한다.")
    void 크리에이터를_구분한다() {
        // given
        Member creator = Creator.builder().build();

        // when
        boolean result = identificationService.isCreator(creator);

        // then
        assertThat(result).isEqualTo(true);
    }
}
