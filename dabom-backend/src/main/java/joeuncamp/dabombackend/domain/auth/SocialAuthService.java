package joeuncamp.dabombackend.domain.auth;

import joeuncamp.dabombackend.global.security.jwt.TokenForm;

public interface SocialAuthService {
    public TokenForm login(String socialToken);

    public void logout(String kakaoToken, String socialToken);

    public void withdraw(String accessToken);
}
