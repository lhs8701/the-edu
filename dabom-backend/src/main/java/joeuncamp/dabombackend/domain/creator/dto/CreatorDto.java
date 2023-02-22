package joeuncamp.dabombackend.domain.creator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

public class CreatorDto {

    @Getter
    @AllArgsConstructor
    public static class Request {
        @Schema(hidden = true)
        Long memberId;
    }

    @Getter
    @NoArgsConstructor
    @Setter
    public static class StandByRequest {
        @Schema(hidden = true)
        Long memberId;
        @Schema(description = "강좌 주제")
        String subject;
        @Schema(description = "경력")
        String career;

        public CreatorProfile toEntity(Member member) {
            return CreatorProfile.builder()
                    .member(member)
                    .subject(subject)
                    .career(career)
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
