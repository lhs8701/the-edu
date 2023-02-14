package joeuncamp.dabombackend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    String paymentKey;
    String orderId;
    Long amount;
}
