package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.domain.order.entity.Order;

public interface PostRefundManager {
    boolean supports(Order order);

    void doAfterAction(Order order);
}
