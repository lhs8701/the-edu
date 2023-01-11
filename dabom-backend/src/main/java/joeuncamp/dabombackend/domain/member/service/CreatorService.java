package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.CreatorRequestDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import org.springframework.stereotype.Service;

@Service
public class CreatorService {
    public boolean hasCreatorProfile(Member member) {
        return member.getCreatorProfile() != null;
    }

    public void activateCreatorProfile(Member member, CreatorRequestDto dto) {
        member.activateCreatorProfile(dto.toEntity());
    }
}
