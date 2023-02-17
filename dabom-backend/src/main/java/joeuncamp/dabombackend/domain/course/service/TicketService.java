package joeuncamp.dabombackend.domain.course.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.dto.TicketDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.TicketJpaRepository;
import joeuncamp.dabombackend.domain.order.entity.CoursePeriod;
import joeuncamp.dabombackend.domain.order.entity.Ticket;
import joeuncamp.dabombackend.global.constant.ChargeType;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final CourseJpaRepository courseJpaRepository;
    private final TicketJpaRepository ticketJpaRepository;

    /**
     * 강좌의 수강권을 조회합니다.
     *
     * @param courseId 조회할 강좌
     * @return 수강권 정보
     */
    public List<TicketDto.Response> getTickets(Long courseId) {
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);
        return course.getTicketList().stream()
                .map(TicketDto.Response::new)
                .toList();
    }

    /**
     * 기본 유료 수강권을 생성합니다.
     * 기존의 수강권은 모두 삭제됩니다.
     * 가격은 초기값인 1000원으로 설정됩니다.
     *
     * @param course 강좌
     */
    @Transactional
    public void createPaidTickets(Course course) {
        ticketJpaRepository.deleteByCourse(course);
        Ticket threeMonthTicket = Ticket.newPaidTicket(course, CoursePeriod.THREE_MONTH);
        Ticket sixMonthTicket = Ticket.newPaidTicket(course, CoursePeriod.SIX_MONTH);
        Ticket unlimitedTicket = Ticket.newPaidTicket(course, CoursePeriod.UNLIMITED);
        List<Ticket> tickets = List.of(threeMonthTicket, sixMonthTicket, unlimitedTicket);
        ticketJpaRepository.saveAll(tickets);
    }

    /**
     * 기본 무료 수강권을 생성합니다.
     * 기존의 수강권은 모두 삭제됩니다.
     *
     * @param course 강좌
     */
    @Transactional
    public void createFreeTicket(Course course) {
        ticketJpaRepository.deleteByCourse(course);
        Ticket freeTicket = Ticket.newFreeTicket(course);
        ticketJpaRepository.save(freeTicket);
    }

    /**
     * 티켓 가격을 변경합니다.
     * 원가와 할인가를 설정할 수 있습니다.
     *
     * @param requestDto 변경할 티켓 정보
     */
    public void updatePrice(TicketDto.Request requestDto) {
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        Ticket ticket = ticketJpaRepository.findByCourseAndCoursePeriod(course, requestDto.getCoursePeriod()).orElseThrow(CResourceNotFoundException::new);
        ticket.updatePrice(requestDto.getCostPrice(), requestDto.getDiscountedPrice());
        ticketJpaRepository.save(ticket);
    }
}
