package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CMemberNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CMemberNotFoundException(){
        super();
        errorCode = ErrorCode.MEMBER_NOT_FOUND;
    }
}
