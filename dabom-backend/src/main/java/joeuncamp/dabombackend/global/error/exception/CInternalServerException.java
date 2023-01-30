package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CInternalServerException extends RuntimeException{
    private final ErrorCode errorCode;

    public CInternalServerException(){
        super();
        errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }
}
