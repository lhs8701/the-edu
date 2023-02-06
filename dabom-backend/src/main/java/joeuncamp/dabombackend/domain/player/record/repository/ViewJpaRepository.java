package joeuncamp.dabombackend.domain.player.record.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.record.entity.View;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ViewJpaRepository extends JpaRepository<View, Long> {
    Optional<View> findByMemberAndUnit(Member member, Unit unit);

    @Query(" select v from View v inner join Unit u on v.unit = u inner join Course c on u.course = c ")
    List<View> findByMemberAndCourse(Member member, @Param("course")Course course);

}
