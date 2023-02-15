package joeuncamp.dabombackend.domain.unit.repository;

import joeuncamp.dabombackend.domain.course.entity.Chapter;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UnitJpaRepository extends JpaRepository<Unit, Long> {
    @Query(" select u from Unit u where u.course = :course order by u.chapter.sequence, u.sequence ")
    List<Unit> findByCourseOrderBySequence(@Param("course") Course course);

    List<Unit> findByCourse(Course course);

    Optional<Unit> findTop1ByChapterOrderBySequenceDesc(Chapter chapter);

    List<Unit> findByChapter(Chapter chapter);
}
