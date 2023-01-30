package joeuncamp.dabombackend.domain.course.repository;

import joeuncamp.dabombackend.domain.course.entity.RankedCourse;
import joeuncamp.dabombackend.global.constant.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingJpaRepository extends JpaRepository<RankedCourse, Long> {
    List<RankedCourse> findAllByCategory(CategoryType categoryType);
}
