package joeuncamp.dabombackend.util.email;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Email {
    @Schema(description = "수신자")
    String to;
    @Schema(description = "제목")
    String subject;
    @Schema(description = "내용")
    String message;
}
