package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("JPA DB로 회원을 저장하고 조회한다.")
    public void JPA_DB로_회원을_저장하고_조회한다() {
        // given
        MemberCreationRequestDto memberCreationRequestDto = MemberCreationRequestDto.builder()
                .account(ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .mobile(ExampleValue.Member.MOBILE)
                .birthDate(ExampleValue.Member.BIRTH_DATE)
                .build();

        // when
        Member createdMember = memberJpaRepository.save(memberCreationRequestDto.toEntity());
        Member foundMember = memberJpaRepository.findById(createdMember.getId()).orElse(null);

        // then
        assertThat(memberCreationRequestDto.getAccount()).isEqualTo(foundMember.getAccount());
    }

    @Test
    @DisplayName("JPA DB로 회원을 저장하고 생성 날짜를 조회한다.")
    public void JPA_DB로_회원을_저장하고_생성_날짜를_조회한다() {
        // given
        MemberCreationRequestDto memberCreationRequestDto = new MemberCreationRequestDto(
                ExampleValue.Member.ACCOUNT,
                ExampleValue.Member.PASSWORD,
                ExampleValue.Member.NICKNAME,
                ExampleValue.Member.MOBILE,
                ExampleValue.Member.BIRTH_DATE
        );

        LocalDate today = LocalDate.of(2023, 1, 6);

        // when
        Member createdMember = memberJpaRepository.save(memberCreationRequestDto.toEntity());
        Member foundMember = memberJpaRepository.findById(createdMember.getId()).orElse(null);
        LocalDate createdDate = foundMember.getCreatedTime().toLocalDate();

        // then
        assertThat(createdDate).isEqualTo(today);
    }
}
