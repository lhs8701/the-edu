package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CResourceNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CResourceNotFoundException(){
        super();
        errorCode = ErrorCode.RESOURCE_NOT_FOUND;
    }
}
