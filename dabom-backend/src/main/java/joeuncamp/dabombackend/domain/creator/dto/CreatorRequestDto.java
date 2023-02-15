package joeuncamp.dabombackend.domain.creator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
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
    @Schema(description = "회원 아이디넘버", example = "1")
    Long memberId;

    public CreatorProfile toEntity(Member member) {
        return CreatorProfile.builder()
                .member(member)
                .build();
    }
}
