package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.course.repository.TicketJpaRepository;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.domain.order.entity.ItemType;
import joeuncamp.dabombackend.domain.order.entity.Order;
import joeuncamp.dabombackend.domain.order.entity.Ticket;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketPostRefundManager implements PostRefundManager {

    private final EnrollService enrollService;
    private final TicketJpaRepository ticketJpaRepository;

    @Override
    public boolean supports(Order order) {
        return order.getItem().getItemType().equals(ItemType.TICKET);
    }

    /**
     * 환불 후속조치로 강좌 등록 정보 및 시청 기록을 삭제합니다.
     *
     * @param order 주문 내역
     */
    @Override
    public void doAfterAction(Order order) {
        Member member = order.getMember();
        Ticket ticket = ticketJpaRepository.findById(order.getItem().getId()).orElseThrow(CResourceNotFoundException::new);
        enrollService.dropOutCourse(member, ticket);
    }
}
