package joeuncamp.dabombackend.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RoundCalculatorTest {

    @ParameterizedTest
    @CsvSource({"3.756, 1, 3.8", "0.564, 2, 0.56"})
    @DisplayName("수를 반올림한다.")
    void 수를_반올림한다(double number, int digit, double expected) {
        // given
        RoundCalculator roundCalculator = new RoundCalculator();

        // when
        double result = roundCalculator.round(number, digit);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }
}
