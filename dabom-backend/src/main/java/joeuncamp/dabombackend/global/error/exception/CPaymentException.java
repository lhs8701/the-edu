package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CPaymentException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String message;

    public CPaymentException(String message){
        super();
        this.errorCode = ErrorCode.PAYMENT_ERROR;
        this.message = message;
    }
}
