package joeuncamp.dabombackend.domain.order.entity;

import joeuncamp.dabombackend.domain.course.dto.CoursePeriodDto;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import joeuncamp.dabombackend.global.error.exception.CCategoryNotFoundException;
import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import joeuncamp.dabombackend.util.tossapi.dto.AuthResultResponse;
import lombok.Getter;

import java.time.Period;
import java.util.Arrays;

@Getter
public enum CoursePeriod {
    THREE_MONTH("3개월 수강권", 50000L, Period.ofMonths(3)),
    SIX_MONTH("6개월 수강권", 100000L, Period.ofMonths(6)),
    UNLIMITED("영구 수강권", 200000L, Period.ofYears(1000)),
    EMPTY("없음", 0L, Period.ofMonths(0));
    final String description;
    final long defaultPrice;
    final Period period;

    CoursePeriod(String description, long defaultPrice, Period period) {
        this.description = description;
        this.defaultPrice = defaultPrice;
        this.period = period;
    }

    public static CoursePeriod findByName(String name) {
        return Arrays.stream(values())
                .filter(elem -> elem.name().equals(name))
                .findAny()
                .orElse(EMPTY);

    }

    public CoursePeriodDto getDto() {
        return new CoursePeriodDto(this.name(), this.description);
    }
}
