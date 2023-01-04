package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberMemoryFindService implements MemberFindService{
    private MemberRepository memberRepository;
    public MemberMemoryFindService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id);
    }
}
