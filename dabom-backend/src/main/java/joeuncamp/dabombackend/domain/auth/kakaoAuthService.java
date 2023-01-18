package joeuncamp.dabombackend.domain.auth;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import joeuncamp.dabombackend.global.security.jwt.JwtProvider;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import joeuncamp.dabombackend.util.kakaoapi.KakaoService;
import joeuncamp.dabombackend.util.kakaoapi.dto.KakaoProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class kakaoAuthService {

    private final JwtProvider jwtProvider;
    private final KakaoService kakaoService;
    private final MemberJpaRepository memberJpaRepository;

    public TokenForm login(String kakaoToken) {
        KakaoProfile profile = kakaoService.getKakaoProfile(kakaoToken);
        String kakaoId = String.valueOf(profile.getId());
        Optional<Member> found = memberJpaRepository.findByLoginTypeAndSocialId(LoginType.KAKAO, kakaoId);
        if (found.isEmpty()){
            Member created = signup(kakaoToken, profile);
            return jwtProvider.generateToken(created);
        }
        return jwtProvider.generateToken(found.get());
    }

    private Member signup(String kakaoToken, KakaoProfile kakaoProfile) {
        return null;
    }

    public void logout() {

    }

    public void withdraw() {

    }
}
