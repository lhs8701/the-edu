package joeuncamp.dabombackend.global.error.exception;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {
    int code;
    String message;

    public ErrorResponseDto(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
