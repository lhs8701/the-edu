package joeuncamp.dabombackend.domain.unit.repository;

import joeuncamp.dabombackend.domain.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitJpaRepository extends JpaRepository<Unit, Long> {
}
