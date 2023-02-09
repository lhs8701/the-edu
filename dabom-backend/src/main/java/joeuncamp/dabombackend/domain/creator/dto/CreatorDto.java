package joeuncamp.dabombackend.domain.creator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

public class CreatorDto {

    @Getter
    @AllArgsConstructor
    public static class Request {
        @Schema(hidden = true)
        Long memberId;
    }

    @Getter
    @AllArgsConstructor
    public static class StandByRequest {
        @Schema(hidden = true)
        Long memberId;

        public CreatorProfile toEntity(Member member) {
            return CreatorProfile.builder()
                    .member(member)
                    .activated(false)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ActivateRequest {
        @Schema(hidden = true)
        Long memberId;

        public CreatorProfile toEntity(Member member) {
            return CreatorProfile.builder()
                    .member(member)
                    .activated(false)
                    .build();
        }
    }


}
