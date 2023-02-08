package joeuncamp.dabombackend.domain.event.repository;

import joeuncamp.dabombackend.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<Event, Long> {
}
