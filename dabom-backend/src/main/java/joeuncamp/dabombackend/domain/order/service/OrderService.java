package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.PayPointManager;
import joeuncamp.dabombackend.domain.order.dto.CouponDto;
import joeuncamp.dabombackend.domain.order.dto.Data;
import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.domain.order.dto.PaymentInfo;
import joeuncamp.dabombackend.domain.order.entity.*;
import joeuncamp.dabombackend.domain.order.repository.CouponJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.IssueJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.ItemJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.OrderJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CMemberNotFoundException;
import joeuncamp.dabombackend.global.error.exception.CPaymentException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import joeuncamp.dabombackend.util.tossapi.TossService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final CouponJpaRepository couponJpaRepository;
    private final CouponService couponService;
    private final IssueJpaRepository issueJpaRepository;
    private final PayPointManager payPointManager;
    private final TossService tossService;

    /**
     * 구매를 위한 주문서 정보를 조회합니다.
     *
     * @param requestDto 구매할 상품, 회원
     * @return 주문서 정보
     */
    public OrderDto.Response getOrderSheet(OrderDto.StatusRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberNotFoundException::new);
        Item item = itemJpaRepository.findById(requestDto.getItemId()).orElseThrow(CResourceNotFoundException::new);
        List<CouponDto.Response> couponList = issueJpaRepository.findByMember(member).stream()
                .map(Issue::getCoupon)
                .filter(coupon -> coupon.isValid(item))
                .map(CouponDto.Response::new)
                .toList();
        return OrderDto.Response.builder()
                .item(item)
                .member(member)
                .couponList(couponList)
                .build();
    }

    /**
     * 결제를 승인합니다.
     *
     * @param requestDto 결제 정보
     */
    public void completeOrder(OrderDto.Request requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberNotFoundException::new);
        Item item = itemJpaRepository.findById(requestDto.getItemId()).orElseThrow(CResourceNotFoundException::new);
        long price = item.getPrice().getDiscountedPrice();
        if (requestDto.getCouponId() != null) {
            Coupon coupon = couponJpaRepository.findById(requestDto.getCouponId()).orElseThrow(CResourceNotFoundException::new);
            price = couponService.useCoupon(member, coupon, item);
        }
        price -= payPointManager.usePoint(member, requestDto.getPoint());
        if (price != requestDto.getTossSecret().getTossAmount()) {
            throw new CPaymentException();
        }
        Data data = requestDto.getTossSecret().toEntity();
        PaymentInfo paymentInfo = tossService.confirmPayment(data);
        Order order = Order.builder()
                .id(paymentInfo.getOrderId())
                .name(paymentInfo.getOrderName())
                .amount(paymentInfo.getTotalAmount())
                .payType(PayType.findByMethod(paymentInfo.getMethod()))
                .item(item)
                .member(member)
                .build();
        log.info("{}",paymentInfo);
        orderJpaRepository.save(order);
    }
}
