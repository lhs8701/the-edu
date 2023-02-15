package joeuncamp.dabombackend.util.tossapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthStatusResponse {
    String resultType;
    Success success;
    Error error;
    public static class Success{
        String status;
    }

    public static class Error{
        String errorCode;
        String reason;
    }
}
