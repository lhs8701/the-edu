package joeuncamp.dabombackend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    RESOURCE_NOT_FOUND(-1000, "해당 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    REVIEW_EXIST(-1001, "이미 후기를 등록하였습니다.", HttpStatus.FORBIDDEN),
    BAD_REQUEST(-1002, "잘못된 접근입니다.", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(-1003, "존재하지 않는 카테고리입니다.", HttpStatus.NOT_FOUND),


    // 6000 ~ : 권한, 인증 에러
    NOT_CREATOR(-6000, "크리에이터가 아닙니다.", HttpStatus.FORBIDDEN),
    LOGIN_FAILED(-6001, "로그인에 실패했습니다.", HttpStatus.UNAUTHORIZED),
    MEMBER_EXIST_ERROR(-6002, "해당 계정이 이미 존재합니다.", HttpStatus.UNAUTHORIZED),
    ALREADY_CREATOR_ERROR(-6003, "이미 크리에이터입니다.", HttpStatus.FORBIDDEN),

    ALREADY_ENROLLED_COURSE(-6004, "이미 등록한 강좌입니다.", HttpStatus.FORBIDDEN),
    ACCESS_DENIED(-6005, "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    REFRESH_TOKEN_EXPIRED(-6006, "리프레시 토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.FORBIDDEN),
    REISSUE_FAILED(-6007, "토큰 재발급에 실패했습니다.", HttpStatus.BAD_REQUEST),
    MEMBER_NOT_FOUND(-6008, "회원을 조회할 수 없습니다.", HttpStatus.UNAUTHORIZED),
    WRONG_PASSWORD(-6009, "잘못된 비밀번호입니다.", HttpStatus.UNAUTHORIZED),


    // 7000 ~ : 시큐리티 에러
    NOT_AUTHORIZED(-7000, "해당 리소스에 접근하기 위한 권한이 없습니다. 시큐리티 권한 인증에 실패했습니다.", HttpStatus.FORBIDDEN),
    AUTHENTICATION_ERROR(-7001, "시큐리티 인증 과정에서 문제가 발생했습니다.", HttpStatus.UNAUTHORIZED),

    JWT_INVALID(-7002, "유효하지 않은 토큰 형식입니다.", HttpStatus.UNAUTHORIZED),
    JWT_EXPIRED(-7003, "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    JWT_BLOCKED(-7004, "사용이 중지된 토큰입니다.", HttpStatus.UNAUTHORIZED),

    //8000 ~ : 클라이언트 에러
    ILLEGAL_ARGUMENT_ERROR(-8000, "잘못된 파라미터입니다.", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR(-8001, "유효성 검증에 실패했습니다.", HttpStatus.BAD_REQUEST),

    COMMUNICATION_FAILED(-8002, "외부 API와의 통신에서 오류가 발생했습니다.", HttpStatus.BAD_REQUEST),

    // 9000 ~ : 서버 에러
    INTERNAL_SERVER_ERROR(-9999, "서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY(-11111, "없음", HttpStatus.OK);

    final int code;
    final String message;
    final HttpStatus statusCode;

    public static ErrorCode findByName(String name){
        return Arrays.stream(values())
                .filter(type -> type.name().equals(name))
                .findAny()
                .orElse(EMPTY);
    }
}
