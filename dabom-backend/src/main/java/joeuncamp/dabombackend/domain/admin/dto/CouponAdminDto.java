package joeuncamp.dabombackend.domain.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.order.entity.Coupon;
import joeuncamp.dabombackend.domain.order.entity.DiscountPolicy;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CouponAdminDto {
    @Getter
    public static class Response {
        @Schema(description = "쿠폰 아이디넘버")
        Long id;
        @Schema(description = "쿠폰 이름")
        String name;
        @Schema(description = "할인률")
        long discount;
        @Schema(description = "정률할인 / 정액할인", example = "RATE / FIX")
        DiscountPolicy discountPolicy;
        @Schema(description = "유효기간")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate endDate;
        @Schema(description = "만료여부")
        boolean expired;
        @Schema(description = "발급된 랜덤 코드")
        List<String> code;

        public Response(Coupon coupon) {
            this.id = coupon.getId();
            this.name = coupon.getName();
            this.discount = coupon.getDiscount();
            this.discountPolicy = coupon.getDiscountPolicy();
            this.endDate = coupon.getEndDate();
            this.expired = coupon.getEndDate().isBefore(LocalDateTime.now().toLocalDate());
            this.code = coupon.getCode();
        }
    }
}
