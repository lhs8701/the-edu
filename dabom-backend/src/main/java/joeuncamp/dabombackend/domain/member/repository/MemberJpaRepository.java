package joeuncamp.dabombackend.domain.member.repository;


import joeuncamp.dabombackend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByAccount(String account);
}
