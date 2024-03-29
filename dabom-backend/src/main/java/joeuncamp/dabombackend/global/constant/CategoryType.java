package joeuncamp.dabombackend.global.constant;

import joeuncamp.dabombackend.global.error.exception.CCategoryNotFoundException;
import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum CategoryType {
    BACK_END("백엔드"),
    FRONT_END("프론트엔드"),
    DATA_PROCESSING("정보처리"),
    ELECTRICAL("전기"),
    TOEIC("토익"),
    TOEFL("토플"),
    STOCK("주식"),
    FUND("펀드");

    private final String title;

    public static CategoryType findByTitle(String title){
        return Arrays.stream(values())
                .filter(type -> type.getTitle().equals(title))
                .findAny()
                .orElseThrow(CCategoryNotFoundException::new);
    }
}
