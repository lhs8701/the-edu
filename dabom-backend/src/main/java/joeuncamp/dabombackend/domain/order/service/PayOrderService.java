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
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import joeuncamp.dabombackend.global.error.exception.CMemberNotFoundException;
import joeuncamp.dabombackend.global.error.exception.CPaymentException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import joeuncamp.dabombackend.util.tossapi.TossService;
import joeuncamp.dabombackend.util.tossapi.dto.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        Item item = itemJpaRepository.findById(requestDto.getItemId()).orElseThrow(CResourceNotFoundException::new);

        long price = item.getPrice().getDiscountedPrice();
        if (requestDto.getCouponId() != null) {
            Coupon coupon = couponJpaRepository.findById(requestDto.getCouponId()).orElseThrow(CResourceNotFoundException::new);
            Issue issue = issueJpaRepository.findByMemberAndCoupon(member, coupon).orElseThrow(CResourceNotFoundException::new);
            price = issueService.useCoupon(issue, item);
        }
        price -= payPointManager.usePoint(member, requestDto.getPoint());
        if (price != requestDto.getTossPayDto().getTossAmount()) {
            throw new CPaymentException();
        }

        PaymentInfo paymentInfo = tossService.confirmPayment(requestDto.getTossPayDto().toEntity());
        Order order = PayOrder.builder()
                .id(paymentInfo.getOrderId())
                .name(paymentInfo.getOrderName())
                .amount(paymentInfo.getTotalAmount())
                .payType(PayType.findByMethod(paymentInfo.getMethod()))
                .item(item)
                .member(member)
                .build();
        return orderJpaRepository.save(order);
    }
}
