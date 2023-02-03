package joeuncamp.dabombackend.domain.wish.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class WishDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        @NotNull
        @Schema(hidden = true)
        Long memberId;
        @NotNull
        @Schema(hidden = true)
        Long courseId;

        public Wish toEntity(Member member, Course course) {
            return Wish.builder()
                    .member(member)
                    .course(course)
                    .build();
        }
    }
}
