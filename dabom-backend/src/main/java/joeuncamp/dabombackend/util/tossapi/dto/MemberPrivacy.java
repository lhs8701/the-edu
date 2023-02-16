package joeuncamp.dabombackend.util.tossapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class MemberPrivacy {
    @Schema(description = "이름", example = "김철수")
    String name;
    @Schema(description = "전화번호", example = "01012345678")
    String mobile;
    @Schema(description = "생년월일", example = "19990101")
    String birthday;
}
