package joeuncamp.dabombackend.domain.auth.service;

import joeuncamp.dabombackend.domain.auth.dto.SocialLoginRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.SocialUnlinkRequestDto;
import joeuncamp.dabombackend.domain.auth.repository.TokenRedisRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.security.jwt.JwtProvider;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import joeuncamp.dabombackend.util.kakaoapi.KakaoService;
import joeuncamp.dabombackend.util.kakaoapi.dto.KakaoProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class kakaoAuthService implements SocialAuthService {

    private final JwtProvider jwtProvider;
    private final KakaoService kakaoApiService;
    private final MemberJpaRepository memberJpaRepository;

    private final TokenRedisRepository tokenRedisRepository;

    /**
     * 카카오 토큰으로 로그인합니다.
     * 카카오 계정의 아이디와 '카카오'로그인 타입을 파라미터로 DB에서 회원을 조회한 후, 없으면 회원을 생성합니다.
     * 어세스토큰과 리프레시토큰을 반환합니다.
     *
     * @param requestDto 카카오에서 발급한 토큰, 리프레시토큰
     * @return 어세스토큰, 리프레시 토큰
     */
    @Override
    public TokenForm login(SocialLoginRequestDto requestDto) {
        KakaoProfile profile = kakaoApiService.getKakaoProfile(requestDto.getSocialToken());
        String kakaoId = String.valueOf(profile.getId());
        Member member = findMemberOrCreate(profile, kakaoId);
        TokenForm tokenForm = jwtProvider.generateToken(member);
        tokenRedisRepository.saveRefreshToken(tokenForm.getRefreshToken(), member.getAccount());
        return tokenForm;
    }

    private Member findMemberOrCreate(KakaoProfile profile, String kakaoId) {
        Optional<Member> found = memberJpaRepository.findByLoginTypeAndSocialId(LoginType.KAKAO, kakaoId);
        if (found.isEmpty()) {
            Member member = profile.toEntity();
            return memberJpaRepository.save(member);
        }
        return found.get();
    }


    /**
     * 카카오 토큰으로 로그아웃합니다.
     * 카카오 로그아웃 API를 호출합니다.
     * 어세스토큰을 Block처리하고, 리프레시토큰을 레디스에서 제거합니다.
     *
     * @param requestDto  카카오에서 발급한 토큰, 리프레시토큰, 어세스토큰
     * @param accessToken 어세스토큰
     */
    @Override
    public void logout(SocialUnlinkRequestDto requestDto, String accessToken) {
        kakaoApiService.logout(requestDto.getSocialToken());
        tokenRedisRepository.saveBlockedToken(accessToken);
        tokenRedisRepository.deleteRefreshToken(requestDto.getRefreshToken());
    }

    /**
     * 계정을 탈퇴합니다.
     * 카카오 Unlink API를 호출한 후, DB에서 계정을 삭제합니다.
     * 로그아웃 로직을 실행합니다.
     *
     * @param requestDto  카카오에서 발급한 토큰, 리프레시토큰
     * @param accessToken 어세스토큰
     */
    @Override
    public void withdraw(SocialUnlinkRequestDto requestDto, String accessToken) {
        kakaoApiService.unlink(requestDto.getSocialToken());
        Member member = (Member) jwtProvider.getAuthentication(accessToken).getPrincipal();
        memberJpaRepository.deleteById(member.getId());
        logout(requestDto, accessToken);
    }
}
