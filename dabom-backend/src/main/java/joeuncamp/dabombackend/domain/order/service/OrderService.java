package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.PayPointManager;
import joeuncamp.dabombackend.domain.order.dto.CouponDto;
import joeuncamp.dabombackend.util.tossapi.dto.ConfirmRequest;
import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.util.tossapi.dto.PaymentInfo;
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
    private final IssueService issueService;
    private final IssueJpaRepository issueJpaRepository;
    private final PayPointManager payPointManager;
    private final TossService tossService;


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
            Issue issue = issueJpaRepository.findByMemberAndCoupon(member, coupon).orElseThrow(CResourceNotFoundException::new);
            price = issueService.useCoupon(issue, item);
        }
        price -= payPointManager.usePoint(member, requestDto.getPoint());
        if (price != requestDto.getTossSecret().getTossAmount()) {
            throw new CPaymentException();
        }
        ConfirmRequest confirmRequest = requestDto.getTossSecret().toEntity();
        PaymentInfo paymentInfo = tossService.confirmPayment(confirmRequest);
        Order order = Order.builder()
                .id(paymentInfo.getOrderId())
                .name(paymentInfo.getOrderName())
                .amount(paymentInfo.getTotalAmount())
                .payType(PayType.findByMethod(paymentInfo.getMethod()))
                .item(item)
                .member(member)
                .build();
        orderJpaRepository.save(order);
    }
}
