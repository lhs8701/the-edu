package joeuncamp.dabombackend.domain.player.record.repository;

import joeuncamp.dabombackend.domain.player.record.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewJpaRepository extends JpaRepository<View, Long> {
}
