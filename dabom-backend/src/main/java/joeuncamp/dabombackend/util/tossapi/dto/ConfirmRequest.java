package joeuncamp.dabombackend.util.tossapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmRequest {
    @Schema(description = "toss paymentKey")
    String tossPaymentKey;
    @Schema(description = "toss orderId")
    String tossOrderId;
    @Schema(description = "toss amount")
    long tossAmount;
}
