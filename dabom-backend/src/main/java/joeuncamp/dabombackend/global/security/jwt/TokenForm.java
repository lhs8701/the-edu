package joeuncamp.dabombackend.global.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class TokenForm {
    Long memberId;
    String grantType;
    String accessToken;
    String refreshToken;
}
