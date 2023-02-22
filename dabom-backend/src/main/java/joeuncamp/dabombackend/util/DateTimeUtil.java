package joeuncamp.dabombackend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Slf4j
public class DateTimeUtil {

    public void temp() {
        LocalDateTime createdTime = LocalDateTime.of(2020, 10, 9, 12, 12, 20);
        LocalDate memberJoinedDate = createdTime.toLocalDate();
        LocalDate startDate = YearMonth.from(memberJoinedDate).atDay(1);
        LocalDate endDate = YearMonth.from(startDate).atEndOfMonth();
        while (startDate.isBefore(LocalDate.now())) {
            log.info("startDate:{}", startDate);
            log.info("endDate:{}", endDate);
            startDate = startDate.plusMonths(1);
            endDate = YearMonth.from(startDate).atEndOfMonth();
        }

    }
}
