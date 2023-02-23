package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CCertificationFailedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CCertificationFailedException(){
        super();
        errorCode = ErrorCode.CERTIFICATION_FAILED;
    }
}
