package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreatorProfileJpaRepository extends JpaRepository<CreatorProfile, Long> {
    Optional<CreatorProfile> findByMember(Member member);
}
