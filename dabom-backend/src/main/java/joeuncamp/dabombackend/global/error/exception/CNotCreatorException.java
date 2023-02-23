package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CNotCreatorException extends RuntimeException{
    private final ErrorCode errorCode;

    public CNotCreatorException(){
        super();
        errorCode = ErrorCode.NOT_CREATOR;
    }
}