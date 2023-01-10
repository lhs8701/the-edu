package joeuncamp.dabombackend.domain.auth.basic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginRequestDto {
    @Email(message = ValidationMessage.NOT_VALID_EMAIL)
    @Schema(description = "계정", example = ExampleValue.Member.ACCOUNT)
    String account;
    //    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$" , message = ValidationMessage.NOT_VALID_PASSWORD)
    @Schema(description = "비밀번호", example = ExampleValue.Member.PASSWORD)
    String password;
}
