package joeuncamp.dabombackend.util;

import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import org.springframework.stereotype.Component;

import static java.lang.Math.pow;

public class RoundCalculator {

    /**
     * 수를 반올림하여 소수점 digit자리 까지 나타낸다.
     *
     * @param number 반올림 할 수
     * @param digit  나타낼 자리수
     * @return 반올림한 수
     */
    public static double round(double number, int digit) {
        if (digit < 0) {
            throw new CIllegalArgumentException("자릿수는 0 이상이어야 합니다.");
        }
        double factor = pow(10, digit);
        return Math.round(number * factor) / factor;
    }
}
