package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Price {
    long costPrice;
    long discountedPrice;
}
