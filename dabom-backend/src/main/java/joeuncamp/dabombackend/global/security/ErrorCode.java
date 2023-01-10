package joeuncamp.dabombackend.global.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 7000 ~ : 시큐리티 에러
    NOT_AUTHORIZED(-7000, "해당 리소스에 접근하기 위한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    AUTHENTICATION_ERROR (-7001, "인증 과정에서 문제가 발생했습니다.", HttpStatus. UNAUTHORIZED),

    JWT_INVALID(-7002, "유효하지 않은 토큰 형식입니다.", HttpStatus.UNAUTHORIZED),
    JWT_EXPIRED(-7003, "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),

    // 9000 ~ : 서버 에러
    INTERNAL_SERVER_ERROR(-9999, "서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    final int code;
    final String message;
    final HttpStatus statusCode;
}
