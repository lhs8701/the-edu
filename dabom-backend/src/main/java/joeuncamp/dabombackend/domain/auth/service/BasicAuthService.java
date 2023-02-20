package joeuncamp.dabombackend.domain.auth.service;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.auth.dto.*;
import joeuncamp.dabombackend.domain.auth.repository.EmailAuthKeyRedisRepository;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicAuthService {

    private final MemberJpaRepository memberJpaRepository;

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final PasswordEncoder passwordEncoder;
    private final TokenRedisRepository tokenRedisRepository;
    private final EmailCertificationService emailCertificationService;
    private final EmailAuthKeyRedisRepository emailAuthKeyRedisRepository;

    /**
     * 회원가입합니다.
     * DB에 회원을 저장합니다.
     *
     * @param requestDto 개인정보
     */
    public void signup(BasicAuthDto.SignupRequest requestDto) {
        if (emailAuthKeyRedisRepository.findByEmail(requestDto.getAccount()).isPresent()) {
            log.info("이메일 인증 중복");
            throw new CBadRequestException("해당 이메일로 가입 중인 계정이 존재합니다. 메일을 확인해주세요.");
        }
        Optional<Member> memberOptional = memberJpaRepository.findByAccount(requestDto.getAccount());
        if (memberOptional.isPresent()) {
            if (memberOptional.get().isEmailCertified()) {
                throw new CMemberExistException();
            }
            memberJpaRepository.delete(memberOptional.get());
        }
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = requestDto.toEntity(encodedPassword);
        memberJpaRepository.save(member);
        emailCertificationService.sendCertificationLink(member.getEmail());
        log.info("이메일 발송 완료");
    }



    /**
     * 로그인합니다.
     * 이메일 인증이 안된 계정의 경우 예외가 발생합니다.
     * 어세스토큰과 리프레시토큰을 반환합니다.
     *
     * @param requestDto 계정, 비밀번호
     * @return 어세스토큰, 리프레시토큰
     */
    @Transactional
    public BasicAuthDto.LoginResponse login(BasicAuthDto.LoginRequest requestDto) {
        Member member = memberJpaRepository.findByAccountAndLoginType(requestDto.getAccount(), LoginType.BASIC).orElseThrow(CMemberNotFoundException::new);
        if (!member.isEmailCertified()) {
            throw new CMemberNotCertifiedException();
        }
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new CWrongPasswordException();
        }
        TokenForm tokenForm = jwtProvider.generateToken(member);
        tokenRedisRepository.saveRefreshToken(tokenForm.getRefreshToken(), member.getAccount());
        return new BasicAuthDto.LoginResponse(member, tokenForm);
    }

    /**
     * 로그아웃합니다.
     * 어세스토큰을 Block처리하고, 리프레시토큰을 레디스에서 제거합니다.
     *
     * @param requestDto 어세스토큰, 리프레시토큰
     */
    @Transactional
    public void logout(BasicAuthDto.UnlinkRequestDto requestDto) {
        tokenRedisRepository.saveBlockedToken(requestDto.getAccessToken());
        tokenRedisRepository.deleteRefreshToken(requestDto.getRefreshToken());
    }

    /**
     * 회원을 탈퇴합니다.
     * DB에서 회원을 삭제하고, 로그아웃 로직을 실행합니다.
     *
     * @param requestDto 어세스토큰, 리프레시토큰
     */
    @Transactional
    public void withdraw(BasicAuthDto.UnlinkRequestDto requestDto) {
        Member member = (Member) jwtProvider.getAuthentication(requestDto.getAccessToken()).getPrincipal();
        memberJpaRepository.deleteById(member.getId());
        logout(requestDto);
    }


    /**
     * 어세스토큰과 리프레시토큰을 재발급합니다.
     * 리프레시토큰도 만료되면 리이슈에 실패합니다.
     *
     * @param requestDto 어세스토큰, 리프레시토큰
     * @return 재발급한 어세스토큰, 리프레시토큰
     */
    @Transactional
    public TokenForm reissue(BasicAuthDto.ReissueRequest requestDto) {
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
