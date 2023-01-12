package joeuncamp.dabombackend.global.error;

import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.global.error.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(CCreationDeniedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CCreationDeniedException e){
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CIllegalArgumentException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CIllegalArgumentException e){
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CLoginFailedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CLoginFailedException e){
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CMemberExistException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CMemberExistException e){
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CResourceNotFoundException e){
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
