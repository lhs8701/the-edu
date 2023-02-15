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
import joeuncamp.dabombackend.util.tossapi.dto.TossPayRequest;
import joeuncamp.dabombackend.util.tossapi.dto.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
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

    /**
     * 상품에 맞는 구매 후속조치를 선택합니다.
     *
     * @param item 상품
     * @return 구매 후속조치
     */
    public PostOrderManager findPostOrderManager(Item item) {
        for (PostOrderManager postOrderManager : postOrderManagers) {
            if (postOrderManager.supports(item)) {
                return postOrderManager;
            }
        }
        throw new CBadRequestException("OrderService 매핑 실패");
    }

    /**
     * 주문을 완료합니다.
     * 이후, 상품의 종류에 따라 적절한 후속 조치가 취해집니다.
     *
     * @param requestDto     request
     */
    public void makeOrder(OrderDto.Request requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberNotFoundException::new);
        Item item = itemJpaRepository.findById(requestDto.getItemId()).orElseThrow(CResourceNotFoundException::new);
        PostOrderManager postOrderManager = findPostOrderManager(item);

        long price = item.getPrice().getDiscountedPrice();
        if (requestDto.getCouponId() != null) {
            Coupon coupon = couponJpaRepository.findById(requestDto.getCouponId()).orElseThrow(CResourceNotFoundException::new);
            Issue issue = issueJpaRepository.findByMemberAndCoupon(member, coupon).orElseThrow(CResourceNotFoundException::new);
            price = issueService.useCoupon(issue, item);
        }
        price -= payPointManager.usePoint(member, requestDto.getPoint());
        if (price != requestDto.getTossPayRequest().getTossAmount()) {
            throw new CPaymentException();
        }

        PaymentInfo paymentInfo = tossService.confirmPayment(requestDto.getTossPayRequest());
        Order order = Order.builder()
                .id(paymentInfo.getOrderId())
                .name(paymentInfo.getOrderName())
                .amount(paymentInfo.getTotalAmount())
                .payType(PayType.findByMethod(paymentInfo.getMethod()))
                .item(item)
                .member(member)
                .build();
        orderJpaRepository.save(order);
        postOrderManager.doAfterOrderAction(member, item);
    }
}
