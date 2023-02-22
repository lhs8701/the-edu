package joeuncamp.dabombackend.domain.post.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.post.entity.Review;
import org.checkerframework.framework.qual.EnsuresQualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByCourse(Course course);

    Optional<Review> findByMemberAndCourse(Member member, Course course);

    @Query(" select avg(r.score) from Review r where r.course = :course ")
    Long findScoreByCourse(@Param("course") Course course);
}
