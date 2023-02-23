package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
        @Min(0) @Max(5000000)
        Long costPrice;
        @Schema(description = "변경할 할인가")
        @Min(0) @Max(5000000)
        Long discountedPrice;
        @Schema(description = "수강 기간")
        Integer coursePeriod;
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
        Integer coursePeriod;

        public Response(Ticket ticket){
            this.id = ticket.getId();
            this.costPrice = ticket.getPrice().getCostPrice();
            this.discountedPrice = ticket.getPrice().getDiscountedPrice();
            this.coursePeriod = ticket.getCoursePeriod();
        }
    }
}
