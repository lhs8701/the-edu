package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EnrollDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        @NotNull
        @Schema(description = "회원 아이디넘버", example = "1")
        Long memberId;
        @NotNull
        @Schema(description = "강좌 아이디넘버", example = "1")
        Long courseId;
    }
}