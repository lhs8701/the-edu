package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberMemoryRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberMemoryFindService implements MemberFindService{
    private MemberMemoryRepository memberRepository;

    public MemberMemoryFindService(MemberMemoryRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id);
    }
}
