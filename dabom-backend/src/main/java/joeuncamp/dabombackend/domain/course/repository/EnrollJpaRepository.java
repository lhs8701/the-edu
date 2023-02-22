package joeuncamp.dabombackend.domain.course.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnrollJpaRepository extends JpaRepository<Enroll, Long> {
    Optional<Enroll> findByMemberAndCourse(Member member, Course course);

    List<Enroll> findAllByMember(Member member);

    List<Enroll> findByMember(Member member, Pageable pageable);

    @Query("select count(e.id) from Enroll e inner join Course c on e.course = :course ")
    Long countByCourse(@Param("course") Course course);

    List<Enroll> findByCourse(Course course);
}
