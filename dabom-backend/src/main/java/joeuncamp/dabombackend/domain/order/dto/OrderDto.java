package joeuncamp.dabombackend.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.util.tossapi.dto.TossPayRequest;
import lombok.*;

public class OrderDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long itemId;
        @Schema(description = "사용한 쿠폰 아이디넘버")
        Long couponId;
        @Schema(description = "사용한 포인트 금액")
        long point;
        TossPayDto tossPayDto;

        @Getter
        @NoArgsConstructor
        public static class TossPayDto {
            @Schema(description = "toss paymentKey")
            String tossPaymentKey;
            @Schema(description = "toss orderId")
            String tossOrderId;
            @Schema(description = "toss amount")
            long tossAmount;

            public TossPayRequest toEntity(){
                return new TossPayRequest(tossPaymentKey, tossOrderId, tossAmount);
            }
        }
    }
}
