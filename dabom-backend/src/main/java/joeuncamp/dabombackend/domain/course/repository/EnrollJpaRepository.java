package joeuncamp.dabombackend.domain.course.repository;

import joeuncamp.dabombackend.domain.course.entity.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollJpaRepository extends JpaRepository<Enroll, Long> {
}
