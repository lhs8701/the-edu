package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        System.out.println("foundMember = " + foundMember.getAccount());

        // then
        assertThat(personalData.getAccount()).isEqualTo(foundMember.getAccount());
    }
}
