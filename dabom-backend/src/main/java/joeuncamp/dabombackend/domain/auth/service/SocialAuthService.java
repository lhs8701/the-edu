package joeuncamp.dabombackend.domain.auth.service;

import joeuncamp.dabombackend.domain.auth.dto.KakaoLoginRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.SocialUnlinkRequestDto;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;

public interface SocialAuthService {
    public TokenForm login(KakaoLoginRequestDto kakaoLoginRequestDto);

    public void logout(SocialUnlinkRequestDto socialUnlinkRequestDto, String accessToken);

    public void withdraw(SocialUnlinkRequestDto socialUnlinkRequestDto, String accessToken);
}
