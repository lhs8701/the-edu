package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Table(name = "orders")
public abstract class Order extends BaseTimeEntity {
    @Id
    String id;
    String name;
    @OneToOne
    @JoinColumn
    Item item;
    @OneToOne
    @JoinColumn
    Member member;
}
