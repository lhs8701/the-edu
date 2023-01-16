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

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishRequestDto {
    @NotNull
    @Schema(description = "회원 아이디넘버", example = "1")
    Long memberId;
    @NotNull
    @Schema(description = "강좌 아이디넘버", example = "1")
    Long courseId;

    public Wish toEntity(Member member, Course course) {
        return Wish.builder()
                .member(member)
                .course(course)
                .build();
    }
}
