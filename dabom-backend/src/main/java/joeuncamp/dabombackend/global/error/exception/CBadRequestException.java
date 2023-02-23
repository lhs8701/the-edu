package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CBadRequestException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String message;

    public CBadRequestException(String message){
        super();
        this.errorCode = ErrorCode.BAD_REQUEST;
        this.message = message;
    }
}
