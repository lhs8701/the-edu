package joeuncamp.dabombackend.domain.order.repository;

import joeuncamp.dabombackend.domain.order.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
}
