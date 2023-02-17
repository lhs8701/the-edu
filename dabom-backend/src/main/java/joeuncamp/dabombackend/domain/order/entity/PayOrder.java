package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
@DiscriminatorValue("pay_order")
public class PayOrder extends Order {
    long amount;
    @Enumerated(value = EnumType.STRING)
    PayType payType;

    @Builder
    public PayOrder(String id, String name, Item item, Member member, long amount, PayType payType){
        this.id = id;
        this.name = name;
        this.item = item;
        this.member = member;
        this.amount = amount;
        this.payType = payType;
    }
}
