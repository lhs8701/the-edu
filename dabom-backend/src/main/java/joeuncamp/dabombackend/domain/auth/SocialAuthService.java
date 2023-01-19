package joeuncamp.dabombackend.domain.auth;

import joeuncamp.dabombackend.global.security.jwt.TokenForm;

public interface SocialAuthService {
    public TokenForm login(SocialTokenRequestDto socialTokenRequestDto);

    public void logout(SocialTokenRequestDto socialTokenRequestDto, String accessToken);

    public void withdraw(SocialTokenRequestDto socialTokenRequestDto, String accessToken);
}
