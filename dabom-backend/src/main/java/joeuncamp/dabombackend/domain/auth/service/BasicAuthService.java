package joeuncamp.dabombackend.domain.auth.service;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.auth.dto.LoginRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.ReissueRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.UnlinkRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.SignupRequestDto;
import joeuncamp.dabombackend.domain.auth.repository.TokenRedisRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.error.exception.*;
import joeuncamp.dabombackend.global.security.jwt.JwtProvider;
import joeuncamp.dabombackend.global.security.jwt.JwtValidator;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BasicAuthService {

    private final MemberJpaRepository memberJpaRepository;

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final PasswordEncoder passwordEncoder;
    private final TokenRedisRepository tokenRedisRepository;

    /**
     * 회원가입합니다.
     * DB에 회원을 저장합니다.
     *
     * @param signUpRequestDto 개인정보
     */
    public void signup(SignupRequestDto signUpRequestDto) {
        if (memberJpaRepository.findByAccount(signUpRequestDto.getAccount()).isPresent()) {
            throw new CMemberExistException();
        }
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        createAndSaveMember(signUpRequestDto, encodedPassword);
    }

    private void createAndSaveMember(SignupRequestDto signUpRequestDto, String encodedPassword) {
        Member member = signUpRequestDto.toEntity(encodedPassword);
        memberJpaRepository.save(member);
    }

    /**
     * 로그인합니다.
     * 어세스토큰과 리프레시토큰을 반환합니다.
     *
     * @param loginRequestDto 계정, 비밀번호
     * @return 어세스토큰, 리프레시토큰
     */
    public TokenForm login(LoginRequestDto loginRequestDto) {
        Member member = memberJpaRepository.findByAccountAndLoginType(loginRequestDto.getAccount(), LoginType.BASIC).orElseThrow(CResourceNotFoundException::new);
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new CWrongPasswordException();
        }
        TokenForm tokenForm = jwtProvider.generateToken(member);
        tokenRedisRepository.saveRefreshToken(tokenForm.getRefreshToken(), member.getAccount());
        return tokenForm;
    }

    /**
     * 로그아웃합니다.
     * 어세스토큰을 Block처리하고, 리프레시토큰을 레디스에서 제거합니다.
     *
     * @param accessToken 어세스토큰
     * @param requestDto  리프레시토큰
     */
    public void logout(UnlinkRequestDto requestDto, String accessToken) {
        tokenRedisRepository.saveBlockedToken(accessToken);
        tokenRedisRepository.deleteRefreshToken(requestDto.getRefreshToken());
    }

    /**
     * 회원을 탈퇴합니다.
     * DB에서 회원을 삭제하고, 로그아웃 로직을 실행합니다.
     *
     * @param accessToken 어세스토큰
     * @param requestDto  리프레시토큰
     */
    public void withdraw(UnlinkRequestDto requestDto, String accessToken) {
        Member member = (Member) jwtProvider.getAuthentication(accessToken).getPrincipal();
        memberJpaRepository.deleteById(member.getId());
        logout(requestDto, accessToken);
    }


    /**
     * 어세스토큰과 리프레시토큰을 재발급합니다.
     * 리프레시토큰도 만료되면 리이슈에 실패합니다.
     *
     * @param requestDto 어세스토큰, 리프레시토큰
     * @return 재발급한 어세스토큰, 리프레시토큰
     */
    public TokenForm reissue(ReissueRequestDto requestDto) {
        isReissueAvailable(requestDto.getAccessToken(), requestDto.getRefreshToken());
        Member member = (Member) jwtProvider.getAuthentication(requestDto.getRefreshToken()).getPrincipal();
        TokenForm tokenForm = jwtProvider.generateToken(member);
        tokenRedisRepository.saveRefreshToken(tokenForm.getRefreshToken(), member.getAccount());
        tokenRedisRepository.deleteRefreshToken(requestDto.getRefreshToken());
        return tokenForm;
    }

    private void isReissueAvailable(String accessToken, String refreshToken) {
        String subject = jwtValidator.validateAccessTokenForReissue(accessToken).getSubject();
        jwtValidator.validateRefreshTokenForReissue(refreshToken);
        String memberId = tokenRedisRepository.findByRefreshToken(refreshToken).orElseThrow(CReissueFailedException::new);
        if (!subject.equals(memberId)) {
            throw new CReissueFailedException();
        }
    }
}
