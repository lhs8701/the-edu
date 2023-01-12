package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.CreatorRequestDto;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAlreadyCreatorException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatorService {
    private final MemberJpaRepository memberJpaRepository;
    private final CreatorProfileJpaRepository creatorProfileJpaRepository;

    public boolean hasCreatorProfile(Member member) {
        return member.getCreatorProfile() != null;
    }

    public void activateCreatorProfile(Long memberId, CreatorRequestDto dto) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        if (member.getCreatorProfile() != null){
            throw new CAlreadyCreatorException();
        }
        saveCreatorProfile(dto, member);
    }

    private void saveCreatorProfile(CreatorRequestDto dto, Member member) {
        CreatorProfile creatorProfile = dto.toEntity(member);
        creatorProfileJpaRepository.save(creatorProfile);
    }
}
