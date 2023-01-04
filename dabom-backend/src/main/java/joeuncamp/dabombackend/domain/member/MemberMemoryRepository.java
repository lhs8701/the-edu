package joeuncamp.dabombackend.domain.member;

import java.util.HashMap;
import java.util.Map;

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
        return store.values().stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }
}
