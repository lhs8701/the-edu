package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.PayPointManager;
import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.domain.order.repository.CouponJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.IssueJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.ItemJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.OrderJpaRepository;
import joeuncamp.dabombackend.util.tossapi.TossService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CourseOrderService extends OrderService{
    public CourseOrderService(OrderJpaRepository orderJpaRepository, MemberJpaRepository memberJpaRepository, ItemJpaRepository itemJpaRepository, CouponJpaRepository couponJpaRepository, IssueService issueService, IssueJpaRepository issueJpaRepository, PayPointManager payPointManager, TossService tossService) {
        super(orderJpaRepository, memberJpaRepository, itemJpaRepository, couponJpaRepository, issueService, issueJpaRepository, payPointManager, tossService);
    }

    public void completeOrder(OrderDto.Request requestDto) {
        super.completeOrder(requestDto);
    }
}
