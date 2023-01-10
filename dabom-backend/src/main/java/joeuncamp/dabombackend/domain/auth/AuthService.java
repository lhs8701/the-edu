package joeuncamp.dabombackend.domain.auth;

import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.CAuthenticationException;
import joeuncamp.dabombackend.global.error.CResourceNotFoundException;
import joeuncamp.dabombackend.global.error.CUserExistException;
import joeuncamp.dabombackend.global.security.JwtProvider;
import joeuncamp.dabombackend.global.security.TokenForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberJpaRepository memberJpaRepository;

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignUpRequestDto signUpRequestDto) {
        if (memberJpaRepository.findByAccount(signUpRequestDto.getAccount()).isPresent()) {
            throw new CUserExistException();
        }
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        Member member = signUpRequestDto.toEntity(encodedPassword);
        memberJpaRepository.save(member);
    }

    public TokenForm login(LoginRequestDto loginRequestDto) {
        Member member = memberJpaRepository.findByAccount(loginRequestDto.getAccount()).orElseThrow(CResourceNotFoundException::new);

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new CAuthenticationException();
        }
        return jwtProvider.generateToken(member);
    }

    public void AuthenticationTest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("{}", authentication.getPrincipal());
        log.info("{}", authentication.getCredentials());
        log.info("{}", authentication.isAuthenticated());

        log.info("");
    }
}
