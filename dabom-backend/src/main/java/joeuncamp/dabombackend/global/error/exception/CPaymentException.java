package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CPaymentException extends RuntimeException{
    private final ErrorCode errorCode;

    public CPaymentException(){
        super();
        errorCode = ErrorCode.PAYMENT_ERROR;
    }
}
