package joeuncamp.dabombackend.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Timer;

public class TimerTest {

    @Test
    @DisplayName("특정 시간으로부터 일주일이 지난 상황이면, 특정 로직을 실행한다.")
    void 특정_시간으로부터_일주일이_지난_상황이면_특정_로직을_실행한다() {
        // given
        Timer timer = new Timer();
        long delay = 3000L;
        System.out.println(new Date() + " : Scheduling....");

        // when

        // then
    }
}
