package joeuncamp.dabombackend.domain.auth;

import joeuncamp.dabombackend.global.security.jwt.TokenForm;

public interface SocialAuthService {
    public TokenForm login(String socialToken);

    public void logout(String socialToken, String accessToken);

    public void withdraw(String socialToken, String accessToken);
}
