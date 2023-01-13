package joeuncamp.dabombackend.domain.member.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatorRequestDto {
    @NotNull
    Long memberId;

    public CreatorProfile toEntity(Member member) {
        return CreatorProfile.builder()
                .member(member)
                .build();
    }
}
