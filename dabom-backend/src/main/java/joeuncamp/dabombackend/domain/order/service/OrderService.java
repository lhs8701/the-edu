package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.domain.order.entity.Order;
import joeuncamp.dabombackend.global.error.exception.CMemberNotCertifiedException;

public interface OrderService {
    boolean supports(Item item);
    Order saveOrder(OrderDto.Request requestDto);
}
