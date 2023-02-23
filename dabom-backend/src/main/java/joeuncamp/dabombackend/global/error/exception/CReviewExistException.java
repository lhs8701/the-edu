package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CReviewExistException extends RuntimeException{
    private final ErrorCode errorCode;

    public CReviewExistException(){
        super();
        errorCode = ErrorCode.REVIEW_EXIST;
    }
}