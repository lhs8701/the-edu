package joeuncamp.dabombackend.global.constant;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.service.TicketService;
import joeuncamp.dabombackend.domain.order.entity.Ticket;

import java.util.function.Function;

public enum ChargeType {

    FREE {
        public void createTicket(Course course, TicketService ticketService){
            ticketService.createFreeTicket(course);
        }
    },
    PAID{
        public void createTicket(Course course, TicketService ticketService){
            ticketService.createPaidTickets(course);
        }
    };
    public abstract void createTicket(Course course, TicketService ticketService);
}
