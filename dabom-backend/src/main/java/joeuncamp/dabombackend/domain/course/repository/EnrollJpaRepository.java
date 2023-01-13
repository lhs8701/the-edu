package joeuncamp.dabombackend.domain.course.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollJpaRepository extends JpaRepository<Enroll, Long> {
    Optional<Enroll> findByMemberAndCourse(Member member, Course course);

    List<Enroll> findAllByMember(Member member);
}
