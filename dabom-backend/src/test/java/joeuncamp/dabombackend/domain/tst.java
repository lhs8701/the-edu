package joeuncamp.dabombackend.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class tst {
    @Test
    @DisplayName("")
    void test() {
        // given
        String url = "https://api.tosspayments.com/v1/payments/{paymentKey}/cancel";
        String replaced = url.replace("{paymentKey}", "abc");
        // when

        // then
        Assertions.assertThat(replaced).isEqualTo("https://api.tosspayments.com/v1/payments/abc/cancel");
    }
}
