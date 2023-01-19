package joeuncamp.dabombackend.domain.auth;

import joeuncamp.dabombackend.domain.auth.basic.dto.SignupRequestDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import joeuncamp.dabombackend.global.security.jwt.JwtProvider;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import joeuncamp.dabombackend.util.kakaoapi.KakaoService;
import joeuncamp.dabombackend.util.kakaoapi.dto.KakaoProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class kakaoAuthService implements SocialAuthService{

    private final JwtProvider jwtProvider;
    private final KakaoService kakaoService;
    private final MemberJpaRepository memberJpaRepository;

    public TokenForm login(String kakaoToken) {
        KakaoProfile profile = kakaoService.getKakaoProfile(kakaoToken);
        String kakaoId = String.valueOf(profile.getId());
        Optional<Member> found = memberJpaRepository.findByLoginTypeAndSocialId(LoginType.KAKAO, kakaoId);
        if (found.isEmpty()){
            Member created = signup(profile);
            return jwtProvider.generateToken(created);
        }
        return jwtProvider.generateToken(found.get());
    }

    public Member signup(KakaoProfile kakaoProfile) {
        Member member = kakaoProfile.toEntity();
        return memberJpaRepository.save(member);
    }

    public void logout(String kakaoToken, String accessToken) {
        kakaoService.logout(kakaoToken);
        /* +어세스 토큰 만료 로직 */
    }

    public void withdraw(String accessToken) {
        Member member = (Member) jwtProvider.getAuthentication(accessToken).getPrincipal();
        memberJpaRepository.deleteById(member.getId());
    }
}
