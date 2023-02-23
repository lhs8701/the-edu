package joeuncamp.dabombackend.domain.course.repository;

import joeuncamp.dabombackend.domain.course.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterJpaRepository extends JpaRepository<Chapter, Long> {
    void deleteByCourseId(Long courseId);

    Optional<Chapter> findByIsDefaultIsTrue();

    Optional<Chapter> findTop1ByCourseIdOrderBySequenceDesc(Long courseId);

    List<Chapter> findByCourseId(Long CourseId);

    void deleteAllByCourseId(Long courseId);
}
