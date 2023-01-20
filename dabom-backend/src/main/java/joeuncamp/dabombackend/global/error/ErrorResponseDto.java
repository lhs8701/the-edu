package joeuncamp.dabombackend.global.error;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {
    String name;
    int code;
    String message;

    public ErrorResponseDto(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.name = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
