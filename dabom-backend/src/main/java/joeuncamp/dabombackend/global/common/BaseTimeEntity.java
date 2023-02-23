package joeuncamp.dabombackend.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Schema(description = "생성시각", example="...")
    LocalDateTime createdTime;

    @LastModifiedDate
    @Schema(description = "최근수정시각", example="...")
    LocalDateTime modifiedTime;
}
