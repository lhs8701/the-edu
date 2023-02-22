package joeuncamp.dabombackend.domain.post.repository;

import joeuncamp.dabombackend.domain.post.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyJpaRepository extends JpaRepository<Reply, Long> {
}
