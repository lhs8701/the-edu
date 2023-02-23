package joeuncamp.dabombackend.util.tossapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class TossAuthDto {

    @Getter
    @NoArgsConstructor
    public static class GetRequest{
        String accessToken;
        String txId;
    }
}
