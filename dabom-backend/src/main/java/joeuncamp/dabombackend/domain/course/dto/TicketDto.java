package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import joeuncamp.dabombackend.domain.order.entity.CoursePeriod;
import joeuncamp.dabombackend.domain.order.entity.price.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

public class TicketDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request{
        @Schema(hidden = true)
        Long courseId;
        @Schema(hidden = true)
        Long memberId;
        @Schema(description = "가격")
        long price;
        @Schema(description = "수강 기간", example = "THREE_MONTH / SIX_MONTH / UNLIMITED")
        CoursePeriod coursePeriod;
    }

    @Getter
    public static class Response {
        Long id;
        long price;
        @Enumerated(value = EnumType.STRING)
        CoursePeriodDto coursePeriod;

        public Response(Ticket ticket){
            this.id = ticket.getId();
            this.price = ticket.getPrice();
            this.coursePeriod = ticket.getCoursePeriod().getDto();
        }
    }
}
