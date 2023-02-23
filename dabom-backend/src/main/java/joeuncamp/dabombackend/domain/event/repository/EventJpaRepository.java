package joeuncamp.dabombackend.domain.event.repository;

import joeuncamp.dabombackend.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventJpaRepository extends JpaRepository<Event, Long> {
    List<Event> findByEndDateAfter(LocalDate now);
    List<Event> findByEndDateBefore(LocalDate now);

}
