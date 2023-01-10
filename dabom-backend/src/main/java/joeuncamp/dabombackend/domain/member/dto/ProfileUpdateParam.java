package joeuncamp.dabombackend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProfileUpdateParam {
    @Schema(name="별명", example = ExampleValue.Member.NICKNAME)
    String nickname;

    @Schema(name = "이메일", example = ExampleValue.Member.EMAIL)
    String email;
}
