package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.course.repository.TicketJpaRepository;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.entity.*;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketManager implements PostOrderManager {

    private final EnrollService enrollService;
    private final TicketJpaRepository ticketJpaRepository;

    @Override
    public boolean supports(Item item) {
        return item.getItemType().equals(ItemType.TICKET);
    }

    /**
     * 구매 후속조치로 강좌에 등록정보를 생성합니다.
     *
     * @param member 회원
     * @param item 상품
     */
    @Override
    public void doAfterOrderAction(Member member, Item item) {
        Ticket ticket = ticketJpaRepository.findById(item.getId()).orElseThrow(CResourceNotFoundException::new);
        enrollService.enroll(member, ticket);
    }
}
