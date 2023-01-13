package joeuncamp.dabombackend.domain.wish.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishJpaRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberAndCourse(Member member, Course course);

    List<Wish> findAllByMember(Long memberId);
}
