package joeuncamp.dabombackend.domain.auth.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.auth.dto.AppleAuthDto;
import joeuncamp.dabombackend.domain.auth.repository.TokenRedisRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import joeuncamp.dabombackend.global.error.exception.CMemberNotFoundException;
import joeuncamp.dabombackend.global.security.jwt.JwtProvider;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AppleAuthService {
    private final MemberJpaRepository memberJpaRepository;
    private final TokenRedisRepository tokenRedisRepository;
    private final JwtProvider jwtProvider;

    /**
     * 애플로 회원가입합니다.
     *
     * @param requestDto 애플 토큰에서 추출한 이메일, 소셜 아이디, 닉네임
     */
    public void signup(AppleAuthDto.SignupRequest requestDto) {
        if (memberJpaRepository.findByLoginTypeAndSocialId(LoginType.APPLE, requestDto.getSocialToken()).isPresent()) {
            throw new CMemberExistException();
        }
        createAndSaveMember(requestDto);
    }

    private void createAndSaveMember(AppleAuthDto.SignupRequest requestDto) {
        Member member = requestDto.toEntity();
        memberJpaRepository.save(member);
    }

    /**
     * 애플 로그인합니다.
     * 애플 소셜 아이디와 '애플'로그인 타입을 파라미터로 DB에서 회원을 조회한 후, 없으면 예외를 던집니다.
     * 어세스토큰과 리프레시토큰을 반환합니다.
     *
     * @param requestDto 카카오에서 발급한 토큰, 리프레시토큰
     * @return 어세스토큰, 리프레시 토큰
     */
    public TokenForm login(AppleAuthDto.LoginRequest requestDto) {
        Member member = memberJpaRepository.findByLoginTypeAndSocialId(LoginType.APPLE, requestDto.getSocialToken()).orElseThrow(CMemberNotFoundException::new);
        TokenForm tokenForm = jwtProvider.generateToken(member);
        tokenRedisRepository.saveRefreshToken(tokenForm.getRefreshToken(), String.valueOf(member.getId()));
        return tokenForm;
    }

    /**
     * 애플 로그아웃합니다.
     * 어세스토큰을 Block처리하고, 리프레시토큰을 레디스에서 제거합니다.
     *
     * @param requestDto 리프레시토큰, 어세스토큰
     */
    public void logout(AppleAuthDto.UnlinkRequest requestDto) {
        tokenRedisRepository.saveBlockedToken(requestDto.getAccessToken());
        tokenRedisRepository.deleteRefreshToken(requestDto.getRefreshToken());
    }

    /**
     * 계정을 탈퇴합니다. 회원을 DB에서 삭제합니다.
     * 어세스토큰을 Block처리하고, 리프레시토큰을 레디스에서 제거합니다.
     *
     * @param requestDto 리프레시토큰, 어세스토큰
     */
    public void withdraw(AppleAuthDto.UnlinkRequest requestDto) {
        Member member = (Member) jwtProvider.getAuthentication(requestDto.getAccessToken()).getPrincipal();
        memberJpaRepository.deleteById(member.getId());
        logout(requestDto);
    }
}
