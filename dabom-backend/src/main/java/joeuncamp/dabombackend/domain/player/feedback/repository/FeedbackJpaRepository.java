package joeuncamp.dabombackend.domain.player.feedback.repository;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.feedback.entity.Feedback;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackJpaRepository extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findByMemberAndUnit(Member member, Unit unit);

}
