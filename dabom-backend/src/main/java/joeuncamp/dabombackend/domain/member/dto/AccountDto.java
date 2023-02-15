package joeuncamp.dabombackend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AccountDto {
    @Getter
    @NoArgsConstructor
    public static class Request {
        @Schema(description = "이메일", example = ExampleValue.Member.EMAIL)
        String email;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        @Schema(description = "전달된 이메일", example = ExampleValue.Member.EMAIL)
        String email;
    }
}
