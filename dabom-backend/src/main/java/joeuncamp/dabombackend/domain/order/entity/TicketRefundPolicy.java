package joeuncamp.dabombackend.domain.order.entity;

import org.springframework.stereotype.Component;

@Component
public class TicketRefundPolicy implements RefundPolicy {
    RefundStrategy refundStrategy;

    public TicketRefundPolicy(CompletedNumberStrategy refundStrategy) {
        this.refundStrategy = refundStrategy;
    }
    @Override
    public boolean supports(Order order) {
        return order.getItem().getItemType() == ItemType.TICKET;
    }

    @Override
    public boolean isRefundable(Order order) {
        return refundStrategy.isRefundable(order);
    }
}
