package joeuncamp.dabombackend.domain.player.question.repository;

import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
    @Query("select q from Question q" + " join Unit u on q.unit = u " + "order by q.createdTime desc ")
    Page<Question> findByUnit(Unit unit, Pageable pageable);

}
