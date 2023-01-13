package joeuncamp.dabombackend.domain.wish.repository;

import joeuncamp.dabombackend.domain.wish.entity.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishJpaRepository extends JpaRepository<Wish, Long> {

}
