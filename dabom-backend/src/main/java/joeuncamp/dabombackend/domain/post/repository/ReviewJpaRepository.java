package joeuncamp.dabombackend.domain.post.repository;

import joeuncamp.dabombackend.domain.post.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
