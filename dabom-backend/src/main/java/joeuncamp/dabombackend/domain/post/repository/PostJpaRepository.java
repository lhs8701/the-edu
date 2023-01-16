package joeuncamp.dabombackend.domain.post.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJpaRepository<T extends Post> extends JpaRepository<T, Long> {
    List<T> findAllByCourse(Course course);

}