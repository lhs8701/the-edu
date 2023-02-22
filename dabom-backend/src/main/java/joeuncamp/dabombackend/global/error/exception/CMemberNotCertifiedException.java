package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CMemberNotCertifiedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CMemberNotCertifiedException(){
        super();
        errorCode = ErrorCode.MEMBER_NOT_CERTIFIED;
    }
}
