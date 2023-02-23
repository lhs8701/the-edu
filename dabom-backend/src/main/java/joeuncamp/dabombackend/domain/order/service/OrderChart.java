package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.domain.order.entity.Order;
import joeuncamp.dabombackend.domain.order.repository.OrderJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CMemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderChart {
    private final OrderJpaRepository orderJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    /**
     * 회원의 주문 내역을 조회합니다.
     *
     * @param memberId 회원
     * @return 주문 내역
     */
    public List<OrderDto.Response> getOrderHistory(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CMemberNotFoundException::new);
        List<Order> orders = orderJpaRepository.findByMember(member);
        return orders.stream()
                .map(OrderDto.Response::new)
                .toList();
    }
}
