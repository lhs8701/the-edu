package joeuncamp.dabombackend.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum test {
    BACK_END("백엔드"),
    FRONT_END("프론트엔드"),
    TOEFL("토플"),
    STOCK("주식"),
    FUND("펀드"),
    EMPTY("없음");


    private final String title;

    public static test findByTitle(String title){
        return Arrays.stream(values())
                .filter(type -> type.getTitle().equals(title))
                .findAny()
                .orElse(EMPTY);
    }
}
