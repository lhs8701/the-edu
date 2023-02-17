package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.util.tossapi.dto.PaymentInfo;
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
    public PayOrder(PaymentInfo paymentInfo, Member member, Item item){
        this.id = paymentInfo.getOrderId();
        this.name = paymentInfo.getOrderName();
        this.amount = paymentInfo.getTotalAmount();
        this.payType = PayType.findByMethod(paymentInfo.getMethod());
        this.member = member;
        this.item = item;
    }
}
