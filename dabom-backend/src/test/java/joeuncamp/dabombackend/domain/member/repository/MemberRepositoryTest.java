package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("DB에 회원을 저장하고 조회한다.")
    public void DB에_회원을_저장하고_조회한다() {
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
        assertThat(foundMember.getAccount()).isEqualTo(memberCreationRequestDto.getAccount());
    }

    @Test
    @DisplayName("DB에서 회원을 삭제한다.")
    public void DB에서_회원을_삭제한다() {
        // given
        MemberCreationRequestDto memberCreationRequestDto = MemberCreationRequestDto.builder()
                .account(ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .mobile(ExampleValue.Member.MOBILE)
                .birthDate(ExampleValue.Member.BIRTH_DATE)
                .build();

        Member createdMember = memberJpaRepository.save(memberCreationRequestDto.toEntity());

        // when
        memberJpaRepository.deleteById(createdMember.getId());

        // then
        Optional<Member> found = memberJpaRepository.findById(createdMember.getId());
        assertThat(found.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("DB에 회원을 저장하고 생성 날짜를 조회한다.")
    public void JPA_DB로_회원을_저장하고_생성_날짜를_조회한다() {
        // given
        MemberCreationRequestDto memberCreationRequestDto = MemberCreationRequestDto.builder()
                .account(ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .mobile(ExampleValue.Member.MOBILE)
                .birthDate(ExampleValue.Member.BIRTH_DATE)
                .build();

        LocalDate today = LocalDate.now();

        // when
        Member createdMember = memberJpaRepository.save(memberCreationRequestDto.toEntity());
        Member foundMember = memberJpaRepository.findById(createdMember.getId()).orElse(null);
        LocalDate createdDate = foundMember.getCreatedTime().toLocalDate();

        // then
        assertThat(createdDate).isEqualTo(today);
    }
}
