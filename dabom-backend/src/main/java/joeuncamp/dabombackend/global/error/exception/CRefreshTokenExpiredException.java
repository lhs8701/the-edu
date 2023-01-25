package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CRefreshTokenExpiredException extends RuntimeException{
    private final ErrorCode errorCode;

    public CRefreshTokenExpiredException(){
        super();
        errorCode = ErrorCode.REFRESH_TOKEN_EXPIRED;
    }
}
