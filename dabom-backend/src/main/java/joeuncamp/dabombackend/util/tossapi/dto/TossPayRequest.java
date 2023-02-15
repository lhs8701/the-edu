package joeuncamp.dabombackend.util.tossapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TossPayRequest {
    String paymentKey;
    String orderId;
    long amount;
}
