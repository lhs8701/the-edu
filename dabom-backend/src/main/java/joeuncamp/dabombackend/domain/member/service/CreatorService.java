package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.CreatorRequestDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatorService {
    private final MemberJpaRepository memberJpaRepository;

    public boolean hasCreatorProfile(Member member) {
        return member.getCreatorProfile() != null;
    }

    public void activateCreatorProfile(Long memberId, CreatorRequestDto dto) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        member.activateCreatorProfile(dto.toEntity());
    }
}
