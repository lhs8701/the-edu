package joeuncamp.dabombackend.domain.order.entity;

import joeuncamp.dabombackend.domain.course.dto.CoursePeriodDto;
import lombok.Getter;

@Getter
public enum CoursePeriod {
    THREE_MONTH("3개월", 50000L),
    SIX_MONTH("6개월", 100000L),
    UNLIMITED("영구 소장", 200000L);
    final String description;
    final long defaultPrice;

    CoursePeriod(String description, long defaultPrice) {
        this.description = description;
        this.defaultPrice = defaultPrice;
    }

    public CoursePeriodDto getDto() {
        return new CoursePeriodDto(this.name(), this.description);
    }
}
