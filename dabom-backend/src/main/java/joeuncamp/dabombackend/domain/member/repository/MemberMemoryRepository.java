package joeuncamp.dabombackend.domain.member.repository;

import joeuncamp.dabombackend.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class MemberMemoryRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Long save(Member member) {
        member.setId(++sequence);
        store.put(sequence, member);
        return sequence;
    }

    @Override
    public Member findById(Long id) {
        return store.values().stream().filter(m -> m.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
