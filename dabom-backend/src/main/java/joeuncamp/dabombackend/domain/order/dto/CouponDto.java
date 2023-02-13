package joeuncamp.dabombackend.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.order.entity.DiscountPolicy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CouponDto {
    @Getter
    @NoArgsConstructor
    public static class GenerateRequest {
        @Schema(description = "최소 사용 금액")
        long minimumAmount;
        @Schema(description = "정액할인 / 정률할인(%)", example = "FIX / RATE")
        DiscountPolicy discountPolicy;
        @Schema(description = "할인액")
        long discount;
        @Schema(description = "유효기간")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class IssueRequest {
        @Schema(description = "회원 아이디넘버")
        Long memberId;
        @Schema(description = "발급할 쿠폰")
        Long couponId;
    }
}
