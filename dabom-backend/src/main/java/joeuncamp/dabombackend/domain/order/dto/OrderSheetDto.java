package joeuncamp.dabombackend.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.entity.Item;
import lombok.*;

import java.util.List;

public class OrderSheetDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long itemId;
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
