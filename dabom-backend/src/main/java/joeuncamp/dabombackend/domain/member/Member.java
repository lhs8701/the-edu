package joeuncamp.dabombackend.domain.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "아이디", example = "1")
    Long id;
    @Schema(description = "계정", example = "abc1234")
    String account;
    @Schema(description = "비밀번호", example = "qwer1234")
    String password;
    @Schema(description = "닉네임", example = "헬로")
    String nickname;
    @Schema(description = "전화번호", example = "010-1234-5678")
    String mobile;
    @Schema(description = "권한", example = "일반")
    String role;
    @Schema(description = "생성날짜", example="...")
    LocalDate createdDate;
}
