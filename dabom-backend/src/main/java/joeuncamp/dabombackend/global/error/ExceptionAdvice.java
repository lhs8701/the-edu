package joeuncamp.dabombackend.global.error;

import joeuncamp.dabombackend.global.error.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationErrorResponseDto> handle(MethodArgumentNotValidException e){
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        log.error(errorCode.getMessage());

        List<FieldErrorDto> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
           String field = ((FieldError) error ).getField();
           String message = error.getDefaultMessage();
           errors.add(new FieldErrorDto(field, message));
        });
        return new ResponseEntity<>(new ValidationErrorResponseDto(errorCode, errors), errorCode.getStatusCode());
    }

    // Custom Exception

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

    @ExceptionHandler(CAlreadyCreatorException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CAlreadyCreatorException e){
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CCommunicationFailedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CCommunicationFailedException e){
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CRefreshTokenExpiredException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CRefreshTokenExpiredException e){
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CReissueFailedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CReissueFailedException e){
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CAccessDeniedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CAccessDeniedException e){
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
