package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorProfileJpaRepository extends JpaRepository<CreatorProfile, Long> {
}
