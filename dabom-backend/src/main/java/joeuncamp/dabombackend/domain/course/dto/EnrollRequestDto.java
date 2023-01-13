package joeuncamp.dabombackend.domain.course.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollRequestDto {
    @NotNull
    Long memberId;
    @NotNull
    Long courseId;
}
