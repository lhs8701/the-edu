package joeuncamp.dabombackend.domain.post.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@EntityListeners(AuditingEntityListener.class)
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String content;

    @ManyToOne
    @JoinColumn
    Member member;

    @ManyToOne
    @JoinColumn
    Course course;

    @CreatedDate
    @Schema(description = "생성시각", example="...")
    LocalDateTime createdTime;

    @LastModifiedDate
    @Schema(description = "최근수정시각", example="...")
    LocalDateTime modifiedTime;
}
