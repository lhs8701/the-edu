package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CIllegalArgumentException extends RuntimeException{
    private final ErrorCode errorCode;

    public CIllegalArgumentException(){
        super();
        errorCode = ErrorCode.ILLEGAL_ARGUMENT_ERROR;
    }
}
