package joeuncamp.dabombackend.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.order.entity.Coupon;
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

    @Getter
    public static class Response{
        @Schema(description = "쿠폰 이름")
        String name;
        @Schema(description = "할인률")
        long discount;
        @Schema(description = "정률할인 / 정액할인", example = "RATE / FIX")
        DiscountPolicy discountPolicy;
        @Schema(description = "유효기간")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate endDate;
        public Response(Coupon coupon){
            this.name = coupon.getName();
            this.discount = coupon.getDiscount();
            this.discountPolicy = coupon.getDiscountPolicy();
            this.endDate = coupon.getEndDate();
        }
    }
}
