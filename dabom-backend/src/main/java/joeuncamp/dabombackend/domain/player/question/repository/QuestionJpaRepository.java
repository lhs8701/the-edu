package joeuncamp.dabombackend.domain.player.question.repository;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
    Page<Question> findByUnitOrderByCreatedTimeDesc(Unit unit, Pageable pageable);

    Page<Question> findByUnitAndMemberOrderByCreatedTimeDesc(Unit unit, Member member, Pageable pageable);

}
