package joeuncamp.dabombackend.domain.post.repository;

import joeuncamp.dabombackend.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
