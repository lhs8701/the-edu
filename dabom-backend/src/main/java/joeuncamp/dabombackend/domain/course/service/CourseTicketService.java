package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.TicketDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.TicketJpaRepository;
import joeuncamp.dabombackend.domain.order.entity.CoursePeriod;
import joeuncamp.dabombackend.domain.order.entity.Ticket;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseTicketService {
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
     * 기본 수강권을 생성합니다.
     * 가격은 초기값인 1000원으로 설정됩니다.
     *
     * @param course
     */
    public void createDefaultTickets(Course course) {
        List<Ticket> tickets = List.of(new Ticket(course, CoursePeriod.THREE_MONTH), new Ticket(course, CoursePeriod.SIX_MONTH), new Ticket(course, CoursePeriod.UNLIMITED));
        ticketJpaRepository.saveAll(tickets);
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
