package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.PayPointManager;
import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.domain.order.entity.*;
import joeuncamp.dabombackend.domain.order.repository.CouponJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.IssueJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.ItemJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.OrderJpaRepository;
import joeuncamp.dabombackend.global.error.exception.*;
import joeuncamp.dabombackend.util.tossapi.TossService;
import joeuncamp.dabombackend.util.tossapi.dto.PaymentInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayOrderService implements OrderService {
    @Autowired
    List<PostOrderManager> postOrderManagers;
    private final OrderJpaRepository orderJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final CouponJpaRepository couponJpaRepository;
    private final IssueService issueService;
    private final IssueJpaRepository issueJpaRepository;
    private final PayPointManager payPointManager;
    private final TossService tossService;

    @Override
    public boolean supports(Item item) {
        return item.getPrice().getDiscountedPrice() > 0;
    }

    /**
     * 주문을 완료합니다.
     * 이후, 상품의 종류에 따라 적절한 후속 조치가 취해집니다.
     *
     * @param requestDto request
     */
    @Override
    public Order saveOrder(OrderDto.Request requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberNotFoundException::new);
        if (!member.isCertified()) {
            throw new CMemberNotCertifiedException();
        }
        Item item = itemJpaRepository.findById(requestDto.getItemId()).orElseThrow(CResourceNotFoundException::new);
        Optional<Coupon> couponOptional = getCoupon(requestDto);
        validateAmount(requestDto, member, item, couponOptional);

        PaymentInfo paymentInfo = tossService.confirmPayment(requestDto.getTossPayDto().toEntity());
        if (couponOptional.isPresent()) {
            Issue issue = issueJpaRepository.findByMemberAndCoupon(member, couponOptional.get()).orElseThrow(CResourceNotFoundException::new);
            issueService.useCoupon(issue, item);
        }
        if (requestDto.getPoint() != 0) {
            payPointManager.usePoint(member, requestDto.getPoint());
        }
        payPointManager.raisePoint(member, (long) (paymentInfo.getTotalAmount() * 0.1));
        Order order = Order.PayOrder(paymentInfo, member, item);
        return orderJpaRepository.save(order);
    }

    private Optional<Coupon> getCoupon(OrderDto.Request requestDto) {
        if (requestDto.getCouponId() == null) {
            return Optional.empty();
        }
        return couponJpaRepository.findById(requestDto.getCouponId());
    }

    private void validateAmount(OrderDto.Request requestDto, Member member, Item item, Optional<Coupon> couponOptional) {
        long originalPrice = item.getPrice().getDiscountedPrice();
        long discountedByCoupon = 0;
        long discountedByPoint = requestDto.getPoint();
        log.info("원래 가격:{}", originalPrice);

        if (couponOptional.isPresent()) {
            validateCoupon(requestDto, member, item);
            discountedByCoupon = couponOptional.get().calculateDiscountedPrice(originalPrice);
        }
        if (requestDto.getPoint() != 0) {
            validatePoint(requestDto, member);
        }
        log.info("쿠폰 할인:{}", discountedByCoupon);
        log.info("포인트 할인:{}", discountedByPoint);
        if (originalPrice - (discountedByCoupon + discountedByPoint) != requestDto.getTossPayDto().getTossAmount()) {
            throw new CPaymentException("결제 금액이 상이합니다.");
        }
    }

    private void validateCoupon(OrderDto.Request requestDto, Member member, Item item) {
        Coupon coupon = couponJpaRepository.findById(requestDto.getCouponId()).orElseThrow(CResourceNotFoundException::new);
        Issue issue = issueJpaRepository.findByMemberAndCoupon(member, coupon).orElseThrow(CResourceNotFoundException::new);
        if (!issueService.isAvailable(issue, item)) {
            throw new CBadRequestException("사용 불가능한 쿠폰입니다.");
        }
        coupon.calculateDiscountedPrice(item.getPrice().getDiscountedPrice());
    }

    private void validatePoint(OrderDto.Request requestDto, Member member) {
        long point = requestDto.getPoint();
        if (member.getPayPoint() < point) {
            throw new CPaymentException("사용할 포인트가 보유한 포인트보다 많습니다.");
        }
    }
}
