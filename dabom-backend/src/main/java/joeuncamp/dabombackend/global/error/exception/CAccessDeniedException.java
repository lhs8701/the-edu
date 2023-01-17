package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;

public class CAccessDeniedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CAccessDeniedException(){
        super();
        errorCode = ErrorCode.ACCESS_DENIED;
    }
}
