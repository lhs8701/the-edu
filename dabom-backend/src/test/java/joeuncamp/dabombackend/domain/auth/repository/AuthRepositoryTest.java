package joeuncamp.dabombackend.domain.auth.repository;

import joeuncamp.dabombackend.domain.auth.dto.SignupRequestDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.util.kakaoapi.dto.KakaoProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("일반 회원가입으로 회원을 생성하고 저장한다.")
    void 일반_회원가입으로_회원을_생성하고_저장한다() {
        // given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .account(ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .build();

        Member member = signupRequestDto.toEntity("encoded");

        // when
        Member saved = memberJpaRepository.save(member);
        Member found = memberJpaRepository.findById(saved.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
    }

    @Test
    @DisplayName("카카오 계정으로 회원을 생성하고 저장한다.")
    void 카카오_계정으로_회원을_생성하고_저장한다() {
        // given
        KakaoProfile kakaoProfile = KakaoProfile.builder()
                .id(1L)
                .email(ExampleValue.Member.EMAIL)
                .nickname(ExampleValue.Member.NICKNAME)
                .profile_image_url("image")
                .thumbnail_image_url("image")
                .is_default_image(false)
                .build();

        Member member = kakaoProfile.toEntity();

        // when
        Member saved = memberJpaRepository.save(member);
        Member found = memberJpaRepository.findById(saved.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
    }
}
