package joeuncamp.dabombackend.util;

import joeuncamp.dabombackend.domain.course.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DateTimeUtil {

    public void temp(Course course) {
        LocalDate openedDate = course.getCreatedTime().toLocalDate();
        LocalDate startDate = YearMonth.from(openedDate).atDay(1);
        LocalDate endDate = YearMonth.from(openedDate).atEndOfMonth();
        Map<Integer, List<LocalDate>> map = new HashMap<>();
        while (startDate.isBefore(LocalDate.now())) {
            if (!map.containsKey(startDate.getYear())){
                map.put(startDate.getYear(), new ArrayList<>());
            }
            /* 로직 시작*/
            //
            map.get(startDate.getYear()).add(startDate);
            map.get(endDate.getYear()).add(endDate);
            /* 로직 끝 */
            startDate = startDate.plusMonths(1);
            endDate = YearMonth.from(startDate).atEndOfMonth();
        }
        for (List<LocalDate> value : map.values()) {
            log.info("-----------------");
            for (LocalDate localDate : value) {
                log.info("{}", localDate);
            }
        }
    }
}
