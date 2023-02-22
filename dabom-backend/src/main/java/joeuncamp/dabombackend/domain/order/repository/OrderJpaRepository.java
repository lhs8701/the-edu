package joeuncamp.dabombackend.domain.order.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderJpaRepository extends JpaRepository<Order, String> {
    List<Order> findByMember(Member member);

    @Query(" select sum(o.amount) from Order o inner join Item i on o.item = i where treat(i as Ticket).course = :course and o.orderStatus = 'DONE' and o.createdTime between :startDate and :endDate ")
    Long findProfitByCourseInDuration(@Param("course") Course course, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(" select count(o.id) from Order o inner join Item i on o.item = i where treat(i as Ticket).course = :course and o.orderStatus = 'CANCELED' ")
    Long countByCourseAndCanceled(Course course);

    @Query(" select sum(o.amount) from Order o inner join Item i on o.item = i inner join Course c on treat(i as Ticket).course = c where c.creatorProfile = :creator and o.orderStatus = 'DONE'")
    Long findProfitByCreator(@Param("creator") CreatorProfile creator);

    @Query(" select sum(o.amount) from Order o inner join Item i on o.item = i where type(i) in (Ticket) and o.orderStatus = 'DONE'")
    Long findTotalProfitInTicket();
    @Query(" select sum(o.amount) from Order o inner join Item i on o.item = i where type(i) in (Ticket) and o.orderStatus = 'DONE' and o.createdTime between :startDate and :endDate ")
    Long findProfitInTicketInDuration(LocalDateTime startDate, LocalDateTime endDate);
}
