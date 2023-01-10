package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.entity.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberManageService {
    private final MemberJpaRepository memberRepository;

    public Long createMember(MemberCreationRequestDto personalData) {
        Member member = personalData.toEntity();
        return memberRepository.save(member).getId();
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow(CResourceNotFoundException::new);
    }
}

