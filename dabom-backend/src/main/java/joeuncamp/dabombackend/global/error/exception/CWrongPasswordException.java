package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CWrongPasswordException extends RuntimeException{
    private final ErrorCode errorCode;

    public CWrongPasswordException(){
        super();
        errorCode = ErrorCode.WRONG_PASSWORD;
    }
}
