package joeuncamp.dabombackend.domain.member;

public class MemberManageService {

    private MemberRepository memberRepository;

    public MemberManageService() {
        this.memberRepository = new MemberMemoryRepository();
    }

    public Long createMember(MemberCreationRequestDto personalData) {
        Member member = personalData.toEntity();
        return memberRepository.save(member);
    }

    public Member getMember(Long id){
        return memberRepository.findById(id);
    }
}
