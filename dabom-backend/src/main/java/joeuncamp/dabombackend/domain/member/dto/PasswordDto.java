package joeuncamp.dabombackend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PasswordDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ResetRequest {
        @Schema(description = "계정", example = ExampleValue.Member.ACCOUNT)
        @Email
        String account;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        @Schema(description = "전달된 이메일", example = ExampleValue.Member.EMAIL)
        String email;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CheckRequest {
        @Schema(hidden = true)
        Long memberId;
        @Schema(description = "비밀번호", example = ExampleValue.Member.PASSWORD)
        String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ChangeRequest {
        @Schema(hidden = true)
        Long memberId;
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$", message = ValidationMessage.NOT_VALID_PASSWORD)
        @Schema(description = "현재 비밀번호", example = ExampleValue.Member.PASSWORD)
        String currentPassword;
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$", message = ValidationMessage.NOT_VALID_PASSWORD)
        @Schema(description = "새 비밀번호", example = ExampleValue.Member.PASSWORD)
        String newPassword;
    }
}
