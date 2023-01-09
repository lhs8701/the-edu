package joeuncamp.dabombackend.global.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class TokenInfo {
    String grantType;
    String accessToken;
    String refreshToken;
}
