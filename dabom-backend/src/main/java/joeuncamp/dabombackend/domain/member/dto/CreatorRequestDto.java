package joeuncamp.dabombackend.domain.member.dto;

import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class CreatorRequestDto {

    public CreatorProfile toEntity(Member member) {
        return CreatorProfile.builder()
                .member(member)
                .build();
    }
}
