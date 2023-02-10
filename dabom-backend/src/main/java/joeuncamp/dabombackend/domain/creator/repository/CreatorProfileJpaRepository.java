package joeuncamp.dabombackend.domain.creator.repository;

import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreatorProfileJpaRepository extends JpaRepository<CreatorProfile, Long> {
    Optional<CreatorProfile> findByMember(Member member);

    List<CreatorProfile> findByActivatedIsFalse();
}
