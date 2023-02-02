package joeuncamp.dabombackend.domain.player.question.repository;

import joeuncamp.dabombackend.domain.player.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
}
