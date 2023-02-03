package joeuncamp.dabombackend.domain.player.answer.repository;

import joeuncamp.dabombackend.domain.player.answer.entity.Answer;
import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerJpaRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findByQuestion(Question question, Pageable pageable);
}
