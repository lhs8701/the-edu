package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class CIllegalArgumentException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String message;

    public CIllegalArgumentException(String message){
        super();
        this.errorCode = ErrorCode.ILLEGAL_ARGUMENT_ERROR;
        this.message = message;
    }
}
