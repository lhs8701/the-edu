package joeuncamp.dabombackend.domain.player.answer.repository;

import joeuncamp.dabombackend.domain.player.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerJpaRepository extends JpaRepository<Answer, Long> {
}
