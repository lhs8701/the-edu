package joeuncamp.dabombackend.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.entity.*;
import joeuncamp.dabombackend.util.tossapi.dto.TossPayRequest;
import lombok.*;

import java.time.LocalDateTime;

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
        @Min(0)
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
    @Getter
    public static class Response{
        @Schema(description = "주문 아이디")
        String orderId;
        @Schema(description = "주문명")
        String orderName;
        @Schema(description = "페이먼트 키")
        String paymentKey;
        @Schema(description = "상품 이미지")
        ImageInfo image;
        @Schema(description = "결제 방법")
        PayType payType;
        @Schema(description = "결제 금액")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        long amount;
        @Schema(description = "주문 상태")
        OrderStatus orderStatus;
        @Schema(description = "주문 날짜")
        LocalDateTime orderTime;

        public Response(Order order){
            this.orderId = order.getId();
            this.orderName = order.getName();
            this.paymentKey = order.getPaymentKey();
            this.image = order.getItem().getImage();
            this.amount = order.getAmount();
            this.payType = order.getPayType();
            this.orderStatus = order.getOrderStatus();
            this.orderTime = order.getCreatedTime();
        }
    }
}
