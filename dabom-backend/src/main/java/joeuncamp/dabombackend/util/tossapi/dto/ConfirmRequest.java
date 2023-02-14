package joeuncamp.dabombackend.util.tossapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRequest {
    String paymentKey;
    String orderId;
    Long amount;
}
