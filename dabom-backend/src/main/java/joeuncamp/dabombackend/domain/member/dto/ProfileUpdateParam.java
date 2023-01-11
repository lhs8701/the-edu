package joeuncamp.dabombackend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Null;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProfileUpdateParam {
    @Null
    @Schema(name="별명", example = ExampleValue.Member.NICKNAME)
    String nickname;

    @Null
    @Schema(name = "이메일", example = ExampleValue.Member.EMAIL)
    String email;
}
