package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CCreationDeniedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CCreationDeniedException(){
        super();
        errorCode = ErrorCode.CREATION_DENIED;
    }
}