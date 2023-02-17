package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.domain.order.entity.Order;

public interface PostOrderManager {
    boolean supports(Item item);
    void doAfterAction(Order order);
}
