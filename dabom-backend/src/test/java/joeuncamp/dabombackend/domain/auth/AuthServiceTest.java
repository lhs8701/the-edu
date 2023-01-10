package joeuncamp.dabombackend.domain.auth;

import joeuncamp.dabombackend.domain.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CLoginFailedException;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import joeuncamp.dabombackend.global.security.jwt.JwtProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    AuthService authService;

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
                .account("original_account")
                .password("original_password")
                .build();

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .account("test_account")
                .password("test_password")
                .build();

        when(memberJpaRepository.findByAccount(loginRequestDto.getAccount())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())).thenReturn(false);

        // when

        // then
        assertThatThrownBy(
                () -> authService.login(loginRequestDto)
        ).isInstanceOf(CLoginFailedException.class);
    }

}
