package joeuncamp.dabombackend.domain.auth.basic;

import joeuncamp.dabombackend.domain.auth.basic.dto.LoginRequestDto;
import joeuncamp.dabombackend.domain.auth.basic.dto.SignUpRequestDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CLoginFailedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import joeuncamp.dabombackend.global.security.jwt.JwtProvider;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
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
            throw new CMemberExistException();
        }
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        createAndSaveMember(signUpRequestDto, encodedPassword);
    }
    private void createAndSaveMember(SignUpRequestDto signUpRequestDto, String encodedPassword) {
        Member member = signUpRequestDto.toEntity(encodedPassword);
        memberJpaRepository.save(member);
    }

    public TokenForm login(LoginRequestDto loginRequestDto) {
        Member member = memberJpaRepository.findByAccount(loginRequestDto.getAccount()).orElseThrow(CResourceNotFoundException::new);

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new CLoginFailedException();
        }
        return jwtProvider.generateToken(member);
    }

    public void logout(){
    }

    public void withdraw(){
    }


    public void AuthenticationTest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("{}", authentication.getPrincipal());
        log.info("{}", authentication.getCredentials());
        log.info("{}", authentication.isAuthenticated());

        log.info("");
    }
}
