package joeuncamp.dabombackend.util.tossapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResultRequest {
    String txId;
    String sessionKey;
}
