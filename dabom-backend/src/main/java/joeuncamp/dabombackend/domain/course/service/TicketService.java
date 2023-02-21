package joeuncamp.dabombackend.domain.course.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.dto.TicketDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.TicketJpaRepository;
import joeuncamp.dabombackend.domain.order.entity.Ticket;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public TicketDto.Response getTicket(Long courseId) {
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);
        return new TicketDto.Response(course.getTicket());
    }

    /**
     * 기본 유료 수강권을 생성합니다.
     * 기존의 수강권은 삭제됩니다.
     * 기간 - 6개월, 가격 - 1000원으로 설정됩니다.
     *
     * @param course 강좌
     */
    @Transactional
    public void createDefaultTickets(Course course) {
        ticketJpaRepository.deleteByCourse(course);
        Ticket ticket = Ticket.newPaidTicket(course, 6);
        ticketJpaRepository.save(ticket);
    }

    /**
     * 티켓 가격과 기간을 변경합니다.
     * 원가와 할인가를 설정할 수 있습니다.
     *
     * @param requestDto 변경할 티켓 정보
     */
    public void updateTicket(TicketDto.Request requestDto) {
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        Ticket ticket = ticketJpaRepository.findByCourse(course).orElseThrow(CResourceNotFoundException::new);
        ticket.update(requestDto.getCostPrice(), requestDto.getDiscountedPrice(), getPeriod(requestDto));
        ticketJpaRepository.save(ticket);
    }

    private Integer getPeriod(TicketDto.Request requestDto) {
        if (requestDto.getDiscountedPrice() == 0) {
            return null;
        }
        return requestDto.getCoursePeriod();
    }
}
