package joeuncamp.dabombackend.domain.auth;

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
    private final KakaoService kakaoService;
    private final MemberJpaRepository memberJpaRepository;

    /**
     * 카카오 토큰으로 로그인합니다.
     * 카카오 계정의 아이디와 '카카오'로그인 타입을 파라미터로 DB에서 회원을 조회한 후, 없으면 회원을 생성합니다.
     *
     * @param requestDto 카카오에서 발급한 토큰
     * @return 어세스토큰, 리프레시 토큰
     */
    @Override
    public TokenForm login(SocialTokenRequestDto requestDto) {
        KakaoProfile profile = kakaoService.getKakaoProfile(requestDto.getSocialToken());
        String kakaoId = String.valueOf(profile.getId());
        Optional<Member> found = memberJpaRepository.findByLoginTypeAndSocialId(LoginType.KAKAO, kakaoId);
        if (found.isEmpty()) {
            Member created = signup(profile);
            return jwtProvider.generateToken(created);
        }
        return jwtProvider.generateToken(found.get());
    }

    private Member signup(KakaoProfile kakaoProfile) {
        Member member = kakaoProfile.toEntity();
        return memberJpaRepository.save(member);
    }

    /**
     * 카카오 토큰으로 로그아웃합니다.
     * 카카오 로그아웃 API를 호출한 후, 어세스토큰과 리프레시토큰을 만료시킵니다.
     *
     * @param requestDto 카카오에서 발급한 토큰
     * @param accessToken 어세스토큰
     */
    @Override
    public void logout(SocialTokenRequestDto requestDto,  String accessToken) {
        kakaoService.logout(requestDto.getSocialToken());
        /* +어세스 토큰 만료 로직 */
    }

    /**
     * 계정을 탈퇴합니다.
     * 카카오 Unlink API를 호출한 후, 어세스토큰과 리프레시토큰을 만료시킵니다.
     * DB에서 회원을 삭제합니다.
     *
     * @param requestDto 카카오에서 발급한 토큰
     * @param accessToken 어세스토큰
     */
    @Override
    public void withdraw(SocialTokenRequestDto requestDto, String accessToken) {
        kakaoService.unlink(requestDto.socialToken);
        /* +어세스 토큰 만료 로직 */
        Member member = (Member) jwtProvider.getAuthentication(accessToken).getPrincipal();
        memberJpaRepository.deleteById(member.getId());
    }
}
