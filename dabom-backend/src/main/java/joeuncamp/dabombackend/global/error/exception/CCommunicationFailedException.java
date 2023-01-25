package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CCommunicationFailedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CCommunicationFailedException(){
        super();
        errorCode = ErrorCode.COMMUNICATION_FAILED;
    }
}
