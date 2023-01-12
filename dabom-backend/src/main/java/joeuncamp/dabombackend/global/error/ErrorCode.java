package joeuncamp.dabombackend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    RESOURCE_NOT_FOUND(-1000, "해당 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 6000 ~ : 권한, 인증 에러
    CREATION_DENIED(-6000, "크리에이터만 강좌 개설을 할 수 있습니다.", HttpStatus.FORBIDDEN),
    LOGIN_FAILED(-6001, "로그인에 실패했습니다.", HttpStatus.UNAUTHORIZED),
    MEMBER_EXIST_ERROR(-6002, "해당 계정이 이미 존재합니다.", HttpStatus.UNAUTHORIZED),
    ALREADY_CREATOR_ERROR(-6003, "이미 크리에이터입니다.", HttpStatus.FORBIDDEN),


    // 7000 ~ : 시큐리티 에러
    NOT_AUTHORIZED(-7000, "해당 리소스에 접근하기 위한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    AUTHENTICATION_ERROR(-7001, "인증 과정에서 문제가 발생했습니다.", HttpStatus.UNAUTHORIZED),

    JWT_INVALID(-7002, "유효하지 않은 토큰 형식입니다.", HttpStatus.UNAUTHORIZED),
    JWT_EXPIRED(-7003, "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),

    //8000 ~ : 클라이언트 에러
    ILLEGAL_ARGUMENT_ERROR(-8000, "잘못된 파라미터입니다.", HttpStatus.BAD_REQUEST),

    // 9000 ~ : 서버 에러
    INTERNAL_SERVER_ERROR(-9999, "서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    final int code;
    final String message;
    final HttpStatus statusCode;
}
