package joeuncamp.dabombackend.util.tossapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse {
    String access_token;

    @Override
    public String toString() {
        return "TokenResponse{" +
                "accessToken='" + access_token + '\'' +
                '}';
    }
}
