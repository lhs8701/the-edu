package joeuncamp.dabombackend.domain.order.entity;

public interface RefundStrategy {
    boolean isRefundable(Order order);
}
