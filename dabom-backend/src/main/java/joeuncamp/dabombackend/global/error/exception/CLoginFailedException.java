package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CLoginFailedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CLoginFailedException(){
        super();
        errorCode = ErrorCode.LOGIN_FAILED;
    }
}
