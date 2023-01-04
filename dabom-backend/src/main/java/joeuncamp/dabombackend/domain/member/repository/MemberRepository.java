package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.domain.member.Member;
import org.springframework.stereotype.Repository;

public interface MemberRepository {
    Long save(Member member);

    Member findById(Long id);
}
