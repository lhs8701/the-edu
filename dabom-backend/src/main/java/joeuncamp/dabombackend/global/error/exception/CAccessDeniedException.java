package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CAccessDeniedException extends RuntimeException{
    ErrorCode errorCode;
    String message;

    public CAccessDeniedException(String message){
        super();
        this.errorCode = ErrorCode.ACCESS_DENIED;
        this.message = message;
    }
}
