package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.order.entity.*;
import joeuncamp.dabombackend.domain.order.repository.OrderJpaRepository;
import joeuncamp.dabombackend.domain.player.record.service.ViewChecker;
import joeuncamp.dabombackend.global.error.exception.*;
import joeuncamp.dabombackend.util.tossapi.TossService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundService {
    private final TossService tossService;
    private final OrderJpaRepository orderJpaRepository;
    @Autowired
    List<RefundPolicy> refundPolicies;

    private RefundPolicy getRefundPolicy(Order order){
        for (RefundPolicy refundPolicy : refundPolicies) {
            if (refundPolicy.supports(order)){
                return refundPolicy;
            }
        }
        throw new CInternalServerException();
    }

    /**
     * 상품을 환불합니다.
     *
     * @param orderId 환불할 상품
     */
    public void refund(String orderId, Member member) {
        Order order = orderJpaRepository.findById(orderId).orElseThrow(CResourceNotFoundException::new);
        if (!order.getMember().getId().equals(member.getId())){
            throw new CAccessDeniedException("구매자 본인만 환불 신청을 할 수 있습니다.");
        }
        if (order.getOrderStatus() != OrderStatus.DONE || order.getPayType() == PayType.EMPTY) {
            throw new CBadRequestException("환불이 불가능한 상품입니다.");
        }
        RefundPolicy refundPolicy = getRefundPolicy(order);
        if (!refundPolicy.isRefundable(order)) {
            throw new CBadRequestException("환불 조건에 해당되지 않습니다.");
        }
        tossService.cancel(order.getPaymentKey());
        order.setOrderStatus(OrderStatus.CANCELED);
        orderJpaRepository.save(order);
    }
}
