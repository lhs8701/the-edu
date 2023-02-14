package joeuncamp.dabombackend.util.tossapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TxIdResponse {
    String resultType;
    Success success;
    Error error;
    @Getter
    public static class Success{
        String txId;
        String authUrl;
    }
    @Getter
    public static class Error{
        String errorCode;
        String reason;
    }
}
