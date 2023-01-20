package joeuncamp.dabombackend.domain.auth.service;

import joeuncamp.dabombackend.domain.auth.dto.SocialLoginRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.SocialUnlinkRequestDto;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;

public interface SocialAuthService {
    public TokenForm login(SocialLoginRequestDto socialLoginRequestDto);

    public void logout(SocialUnlinkRequestDto socialUnlinkRequestDto, String accessToken);

    public void withdraw(SocialUnlinkRequestDto socialUnlinkRequestDto, String accessToken);
}
