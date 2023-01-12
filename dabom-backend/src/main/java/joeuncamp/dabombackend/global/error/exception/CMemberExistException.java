package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CMemberExistException extends RuntimeException{
    private final ErrorCode errorCode;

    public CMemberExistException(){
        super();
        errorCode = ErrorCode.MEMBER_EXIST_ERROR;
    }
}
