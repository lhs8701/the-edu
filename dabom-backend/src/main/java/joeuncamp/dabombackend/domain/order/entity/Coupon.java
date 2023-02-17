package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.query.sqm.TemporalUnit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    List<String> code = new ArrayList<>();
    long minimumAmount;
    long discount;
    @Enumerated(value = EnumType.STRING)
    DiscountPolicy discountPolicy;
    LocalDate endDate;

    public boolean isValid(Item item) {
        return LocalDate.now().isBefore(endDate) && this.getMinimumAmount() <= item.getPrice().getDiscountedPrice();
    }

    public long calculateDiscountedPrice(long price){
        return discountPolicy.calculate(price, discount);
    }

}
