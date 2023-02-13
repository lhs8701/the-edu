package joeuncamp.dabombackend.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.entity.Issue;
import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.domain.order.entity.PayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class OrderDto {
    @Getter
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
        @Schema(description = "구매할 상품 아이디넘버")
        Long itemId;
        @Schema(description = "사용할 쿠폰 아이디넘버")
        Long couponId;
        @Schema(description = "사용할 포인트 금액")
        long point;
        @Schema(description = "결제 방식", example = "CARD / VIRTUAL_ACCOUNT / ACCOUNT_TRANSFER / MOBILE")
        PayType payType;
        @Schema(description = "결제 금액")
        long sum;
    }

    @Getter
    public static class Response{
        String productName;
        String productDetail;
        ImageInfo productImage;
        List<CouponDto.Response> couponList;
        long point;

        public Response(Item item, Member member){
            this.productName = item.getProductName();
            this.productDetail = item.getProductDetail();
            this.productImage = item.getImageInfo();
            this.couponList = member.getIssueList().stream()
                    .map(Issue::getCoupon)
                    .map(CouponDto.Response::new)
                    .toList();
            this.point = member.getPayPoint();
        }
    }
}
