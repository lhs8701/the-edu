package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import joeuncamp.dabombackend.util.tossapi.dto.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "orders")
public class Order extends BaseTimeEntity {
    @Id
    String id;
    String name;
    String paymentKey;
    long amount;
    @Enumerated(value = EnumType.STRING)
    OrderStatus orderStatus;
    @Enumerated(value = EnumType.STRING)
    PayType payType;
    @OneToOne
    @JoinColumn
    Item item;
    @OneToOne
    @JoinColumn
    Member member;

    @Builder
    public Order(PaymentInfo paymentInfo, Member member, Item item){
        this.id = paymentInfo.getOrderId();
        this.name = item.getProductName();
        this.paymentKey = paymentInfo.getPaymentKey();
        this.amount = paymentInfo.getTotalAmount();
        this.orderStatus = OrderStatus.DONE;
        this.payType = PayType.findByMethod(paymentInfo.getMethod());
        this.member = member;
        this.item = item;
    }

    public Order(Member member, Item item){
        this.id = UUID.randomUUID().toString();
        this.name = item.getProductName();
        this.amount = 0;
        this.orderStatus = OrderStatus.DONE;
        this.payType = PayType.EMPTY;
        this.item = item;
        this.member = member;
    }

    public static Order PayOrder(PaymentInfo paymentInfo, Member member, Item item){
        return Order.builder()
                .paymentInfo(paymentInfo)
                .member(member)
                .item(item)
                .build();
    }

    public static Order FreeOrder(Member member, Item item){
        return new Order(member, item);
    }

    public void setOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }
}
