package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.domain.member.repository.MemberMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberManageService {
    private final MemberMemoryRepository memberRepository;

    public Long createMember(MemberCreationRequestDto personalData) {
        Member member = personalData.toEntity();
        return memberRepository.save(member);
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id);
    }
}

