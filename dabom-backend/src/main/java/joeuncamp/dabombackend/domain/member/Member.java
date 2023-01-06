package joeuncamp.dabombackend.domain.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member extends BaseTimeEntity {
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
}
