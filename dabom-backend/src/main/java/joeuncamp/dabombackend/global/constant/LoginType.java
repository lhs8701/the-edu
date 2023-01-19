package joeuncamp.dabombackend.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginType {
    BASIC("일반"),
    KAKAO("카카오"),
    NAVER("네이버"),
    APPLE("애플");
    private final String title;
}
