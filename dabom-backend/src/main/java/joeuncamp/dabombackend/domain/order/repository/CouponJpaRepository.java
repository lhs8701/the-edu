package joeuncamp.dabombackend.domain.order.repository;

import joeuncamp.dabombackend.domain.order.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

}
