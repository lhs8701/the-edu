package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.query.sqm.TemporalUnit;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String code;
    long minimumAmount;
    long discount;
    @Enumerated(value = EnumType.STRING)
    DiscountPolicy discountPolicy;
    LocalDate endDate;

    public boolean isValid(){
        return LocalDate.now().isBefore(endDate.plusDays(1));
    }

}
