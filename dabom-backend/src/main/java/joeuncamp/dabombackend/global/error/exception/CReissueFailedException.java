package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CReissueFailedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CReissueFailedException(){
        super();
        errorCode = ErrorCode.REISSUE_FAILED;
    }
}
