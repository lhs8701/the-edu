package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.domain.order.entity.Order;

public interface OrderService {
    boolean supports(Item item);
    Order saveOrder(OrderDto.Request requestDto);
}
