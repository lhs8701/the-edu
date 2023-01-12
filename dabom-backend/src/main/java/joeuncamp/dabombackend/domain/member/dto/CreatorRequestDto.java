package joeuncamp.dabombackend.domain.member.dto;

import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatorRequestDto {
    String creatorNickname;

    public CreatorProfile toEntity() {
        return CreatorProfile.builder()
                .build();
    }
}
