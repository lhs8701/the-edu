package joeuncamp.dabombackend.domain.order.repository;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.entity.Coupon;
import joeuncamp.dabombackend.domain.order.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueJpaRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByMemberAndUsedIsFalse(Member member);
    Optional<Issue> findByMemberAndCoupon(Member member, Coupon coupon);
}
