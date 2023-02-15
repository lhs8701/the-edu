package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import joeuncamp.dabombackend.domain.order.entity.CoursePeriod;
import joeuncamp.dabombackend.domain.order.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class TicketDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request{
        @Schema(hidden = true)
        Long courseId;
        @Schema(hidden = true)
        Long memberId;
        @Schema(description = "변경할 원가")
        long costPrice;
        @Schema(description = "변경할 할인가")
        long discountedPrice;
        @Schema(description = "수강 기간", example = "THREE_MONTH / SIX_MONTH / UNLIMITED")
        CoursePeriod coursePeriod;
    }

    @Getter
    public static class Response {
        Long id;
        @Schema(description = "원가")
        long costPrice;
        @Schema(description = "할인가")
        long discountedPrice;
        @Schema(description = "수강기간")
        @Enumerated(value = EnumType.STRING)
        CoursePeriodDto coursePeriod;

        public Response(Ticket ticket){
            this.id = ticket.getId();
            this.costPrice = ticket.getPrice().getCostPrice();
            this.discountedPrice = ticket.getPrice().getDiscountedPrice();
            this.coursePeriod = ticket.getCoursePeriod().getDto();
        }
    }
}
