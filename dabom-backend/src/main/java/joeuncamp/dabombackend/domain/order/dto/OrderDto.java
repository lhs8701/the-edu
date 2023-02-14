package joeuncamp.dabombackend.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.util.tossapi.dto.ConfirmRequest;
import lombok.*;

import java.util.List;

public class OrderDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class StatusRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long itemId;
    }

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
        @Schema(description = "토스 정보")
        TossSecret tossSecret;
        @Getter
        @NoArgsConstructor
        public static class TossSecret {
            @Schema(description = "toss paymentKey")
            String tossPaymentKey;
            @Schema(description = "toss orderId")
            String tossOrderId;
            @Schema(description = "toss amount")
            long tossAmount;

            public ConfirmRequest toEntity(){
                return new ConfirmRequest(this.tossPaymentKey, this.tossOrderId, this.tossAmount);
            }
        }

    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        String productName;
        String productDetail;
        ImageInfo productImage;
        List<CouponDto.Response> couponList;
        String consumer;
        long point;

        @Builder
        public Response(Item item, Member member, List<CouponDto.Response> couponList){
            this.productName = item.getProductName();
            this.productDetail = item.getProductDetail();
            this.productImage = item.getImage();
            this.couponList = couponList;
            this.consumer = member.getNickname();
            this.point = member.getPayPoint();
        }
    }
}
