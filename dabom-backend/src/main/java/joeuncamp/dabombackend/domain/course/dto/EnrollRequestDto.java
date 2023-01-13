package joeuncamp.dabombackend.domain.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollRequestDto {
    Long memberId;
    Long courseId;
}
