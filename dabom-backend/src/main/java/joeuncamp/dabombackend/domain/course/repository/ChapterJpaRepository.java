package joeuncamp.dabombackend.domain.course.repository;

import joeuncamp.dabombackend.domain.course.entity.Chapter;
import joeuncamp.dabombackend.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChapterJpaRepository extends JpaRepository<Chapter, Long> {
    void deleteByCourseId(Long courseId);

    Optional<Chapter> findByIsDefaultTrue();

    Optional<Chapter> findTop1ByCourseIdOrderBySequenceDesc(Long courseId);

    List<Chapter> findByCourseId(Long CourseId);
}
