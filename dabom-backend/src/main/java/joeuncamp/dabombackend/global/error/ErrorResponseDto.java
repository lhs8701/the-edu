package joeuncamp.dabombackend.global.error;

import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {
    int status;
    int code;
    String name;
    String message;

    public ErrorResponseDto(ErrorCode errorCode){
        this.status = errorCode.getStatusCode().value();
        this.code = errorCode.getCode();
        this.name = errorCode.name();
        this.message = errorCode.getMessage();
    }

    public ErrorResponseDto(ErrorCode errorCode, String message){
        this.status = errorCode.getStatusCode().value();
        this.code = errorCode.getCode();
        this.name = errorCode.name();
        this.message = errorCode.getMessage() + " " + message;
    }
}
