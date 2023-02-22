package joeuncamp.dabombackend.domain.order.entity;

public interface RefundPolicy {
    boolean supports(Order order);
    boolean isRefundable(Order order);
}
