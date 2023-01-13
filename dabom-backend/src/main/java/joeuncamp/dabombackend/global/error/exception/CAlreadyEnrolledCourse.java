package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CAlreadyEnrolledCourse extends RuntimeException{
    private final ErrorCode errorCode;

    public CAlreadyEnrolledCourse(){
        super();
        errorCode = ErrorCode.ALREADY_ENROLLED_COURSE;
    }
}
