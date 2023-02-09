package joeuncamp.dabombackend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PasswordDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ResetRequest {
        @Schema(description = "계정", example = ExampleValue.Member.ACCOUNT)
        String account;
    }
}
