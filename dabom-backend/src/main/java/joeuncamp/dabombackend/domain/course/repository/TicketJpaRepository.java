package joeuncamp.dabombackend.domain.course.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.order.entity.CoursePeriod;
import joeuncamp.dabombackend.domain.order.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketJpaRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByCourseAndCoursePeriod(Course course, CoursePeriod coursePeriod);

    void deleteByCourse(Course course);
}
