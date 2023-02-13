package joeuncamp.dabombackend.domain.order.repository;

import joeuncamp.dabombackend.domain.order.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueJpaRepository extends JpaRepository<Issue, Long> {

}
