package joeuncamp.dabombackend.domain.order.entity;

import joeuncamp.dabombackend.domain.course.dto.CoursePeriodDto;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import joeuncamp.dabombackend.global.error.exception.CCategoryNotFoundException;
import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CoursePeriod {
    THREE_MONTH("3개월 수강권", 50000L),
    SIX_MONTH("6개월 수강권", 100000L),
    UNLIMITED("영구 수강권", 200000L),
    EMPTY("없음", 0L);
    final String description;
    final long defaultPrice;

    CoursePeriod(String description, long defaultPrice) {
        this.description = description;
        this.defaultPrice = defaultPrice;
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
