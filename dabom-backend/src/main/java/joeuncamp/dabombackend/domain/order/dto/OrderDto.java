package joeuncamp.dabombackend.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    }
}
