package joeuncamp.dabombackend.domain.course.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.constant.CategoryGroup;
import joeuncamp.dabombackend.global.constant.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseJpaRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByCategory(CategoryType categoryType);
    Page<Course> findCourseByCategory(CategoryType categoryType, Pageable pageable);

    @Query("select c from Course c" + " join Review r on r.course = c " + "group by c " + "order by avg(r.score) desc ")
    Page<Course> findCourseByCategoryOrderByScore(CategoryType categoryType, Pageable pageable);

    @Query("select c from Course c" + " join Enroll e on e.course = c " + "group by c " + "order by count(e) desc ")
    Page<Course> findCourseByCategoryOrderByEnrollCount(CategoryType categoryType, Pageable pageable);

    @Query("select c from Course c" + " join Wish w on w.course = c " + "group by c " + "order by count(w) desc ")
    Page<Course> findCourseByCategoryOrderByWishCount(CategoryType categoryType, Pageable pageable);

    Page<Course> findAllByTitleContaining(String keyword, Pageable pageable);
}
