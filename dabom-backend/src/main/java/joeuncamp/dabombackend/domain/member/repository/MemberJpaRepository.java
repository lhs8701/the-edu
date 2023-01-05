package joeuncamp.dabombackend.domain.member.repository;


import joeuncamp.dabombackend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long>{
}
