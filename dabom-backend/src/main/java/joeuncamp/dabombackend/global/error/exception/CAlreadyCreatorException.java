package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CAlreadyCreatorException extends RuntimeException{
    private final ErrorCode errorCode;

    public CAlreadyCreatorException(){
        super();
        errorCode = ErrorCode.ALREADY_CREATOR_ERROR;
    }
}
