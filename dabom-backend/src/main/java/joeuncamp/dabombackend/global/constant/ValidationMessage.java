package joeuncamp.dabombackend.global.constant;

import lombok.Getter;

@Getter
public class ValidationMessage {
    public final static String NOT_VALID_EMAIL = "계정은 이메일 형식이어야 합니다.";
    public static final String NOT_VALID_BIRTH_DATE = "생년월일은 yyyy.mm.dd 형식이어야 합니다.";

}
