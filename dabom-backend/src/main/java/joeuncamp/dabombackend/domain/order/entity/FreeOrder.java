package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@DiscriminatorValue("free_order")
public class FreeOrder extends Order {

    @Builder
    public FreeOrder(String id, String name, Item item, Member member){
        this.id = id;
        this.name = name;
        this.item = item;
        this.member = member;
    }
}
