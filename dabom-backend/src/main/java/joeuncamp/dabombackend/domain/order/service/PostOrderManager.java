package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.entity.Item;

public interface PostOrderManager {
    boolean supports(Item item);
    void doAfterOrderAction(Member member, Item item);
}
