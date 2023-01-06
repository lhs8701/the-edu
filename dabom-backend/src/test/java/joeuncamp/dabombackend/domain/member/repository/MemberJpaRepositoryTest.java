package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("JPA DB로 회원을 저장하고 조회한다.")
    public void JPA_DB로_회원을_저장하고_조회한다(){
        // given
        MemberCreationRequestDto personalData = new MemberCreationRequestDto("test");

        // when
        Member createdMember = memberJpaRepository.save(personalData.toEntity());
        Member foundMember = memberJpaRepository.findById(createdMember.getId()).orElse(null);

        // then
        assertThat(personalData.getAccount()).isEqualTo(foundMember.getAccount());
    }

    @Test
    @DisplayName("JPA DB로 회원을 저장하고 생성 날짜를 조회한다.")
    public void JPA_DB로_회원을_저장하고_생성_날짜를_조회한다(){
        // given
        MemberCreationRequestDto personalData = new MemberCreationRequestDto("test");
        LocalDate today = Year.of(2023).atMonth(1).atDay(6);

        // when
        Member createdMember = memberJpaRepository.save(personalData.toEntity());
        Member foundMember = memberJpaRepository.findById(createdMember.getId()).orElse(null);

        // then
        assertThat(foundMember.getCreatedDate()).isEqualTo(today);
    }
}
