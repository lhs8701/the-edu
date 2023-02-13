package joeuncamp.dabombackend.domain.order.repository;

import joeuncamp.dabombackend.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
