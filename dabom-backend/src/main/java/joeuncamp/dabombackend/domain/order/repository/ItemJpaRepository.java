package joeuncamp.dabombackend.domain.order.repository;

import joeuncamp.dabombackend.domain.order.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {
}
