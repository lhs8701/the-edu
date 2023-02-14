package joeuncamp.dabombackend.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentInfo {
    String orderName;
    String method;
}
