package joeuncamp.dabombackend.domain.auth.basic;

import joeuncamp.dabombackend.domain.auth.dto.BasicAuthDto;
import joeuncamp.dabombackend.domain.auth.service.BasicAuthService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.error.exception.CWrongPasswordException;
import joeuncamp.dabombackend.global.security.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BasicAuthServiceTest {

    @InjectMocks
    BasicAuthService basicAuthService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Mock
    JwtProvider jwtProvider;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 로그인이 실패한다.")
    void 비밀번호가_일치하지_않으면_로그인이_실패한다() {
        // given
        Member member = Member.builder()
                .account(ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .emailCertified(true)
                .build();

        BasicAuthDto.LoginRequest dto = BasicAuthDto.LoginRequest.builder()
                .account(ExampleValue.Member.ACCOUNT)
                .password("invalid_password")
                .build();

        when(memberJpaRepository.findByAccountAndLoginTypeAndLockedIsFalse(dto.getAccount(), LoginType.BASIC)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(dto.getPassword(), member.getPassword())).thenReturn(false);

        // when

        // then
        assertThatThrownBy(() -> basicAuthService.login(dto))
                .isInstanceOf(CWrongPasswordException.class);
    }

}
