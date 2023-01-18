package joeuncamp.dabombackend.domain.auth;

import joeuncamp.dabombackend.global.security.jwt.TokenForm;

public interface AuthService {
    void signup();

    TokenForm login(String kakaoToken);

    void logout();
    void withdraw();
}
