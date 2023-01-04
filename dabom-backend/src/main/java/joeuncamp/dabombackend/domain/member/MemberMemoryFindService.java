package joeuncamp.dabombackend.domain.member;

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
