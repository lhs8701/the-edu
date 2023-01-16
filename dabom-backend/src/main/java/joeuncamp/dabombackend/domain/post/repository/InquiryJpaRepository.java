package joeuncamp.dabombackend.domain.post.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.post.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryJpaRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findAllByCourse(Course course);
}
