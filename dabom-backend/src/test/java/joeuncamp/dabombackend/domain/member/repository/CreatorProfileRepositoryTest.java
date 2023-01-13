package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CreatorProfileRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    CreatorProfileJpaRepository creatorProfileJpaRepository;

    @Test
    @DisplayName("크리에이터 프로필을 생성하면 회원과 연관관계가 맺어진다.")
    void 크리에이터_프로필을_생성하면_회원과_연관관계가_맺어진다() {
        // given
        Member member = Member.builder().build();
        CreatorProfile creatorProfile = CreatorProfile.builder().member(member).build();

        // when
        memberJpaRepository.save(member);
        creatorProfileJpaRepository.save(creatorProfile);
        Member found = creatorProfile.getMember();

        // then
        assertThat(found).isNotNull();
        assertThat(found).isEqualTo(member);
    }
}
