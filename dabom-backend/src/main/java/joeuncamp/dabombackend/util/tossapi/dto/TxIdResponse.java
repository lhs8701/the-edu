package joeuncamp.dabombackend.util.tossapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class TxIdResponse {
    String resultType;
    Success success;
    Error error;
    @Getter
    @ToString
    public static class Success{
        String txId;
        String appScheme;
        String androidAppUri;
        String iosAppUri;
//        String authUrl;
    }
    @Getter
    @ToString
    public static class Error{
        String errorCode;
        String reason;
    }
}
