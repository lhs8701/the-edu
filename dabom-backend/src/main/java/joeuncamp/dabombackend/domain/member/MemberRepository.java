package joeuncamp.dabombackend.domain.member;

public interface MemberRepository {
    Long save(Member member);
    Member findById(Long id);
}
