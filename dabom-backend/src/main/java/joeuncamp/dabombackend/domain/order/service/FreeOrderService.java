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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FreeOrderService implements OrderService {
    @Autowired
    List<PostOrderManager> postOrderManagers;
    private final OrderJpaRepository orderJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ItemJpaRepository itemJpaRepository;

    @Override
    public boolean supports(Item item) {
        return item.getPrice().getDiscountedPrice() == 0;
    }

    /**
     * 무료 상품 주문을 완료합니다.
     * 결제가 이루어지지 않고, 바로 상품의 종류에 따라 적절한 후속 조치가 취해집니다.
     *
     * @param requestDto request
     */
    @Override
    public Order saveOrder(OrderDto.Request requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberNotFoundException::new);
        Item item = itemJpaRepository.findById(requestDto.getItemId()).orElseThrow(CResourceNotFoundException::new);

        long price = item.getPrice().getDiscountedPrice();
        if (price != 0) {
            throw new CBadRequestException("무료 상품 주문에 실패했습니다.");
        }

        Order order = FreeOrder.builder()
                .id(UUID.randomUUID().toString())
                .name(item.getProductName())
                .item(item)
                .member(member)
                .build();
        return orderJpaRepository.save(order);
    }
}
