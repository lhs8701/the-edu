package joeuncamp.dabombackend.domain.post.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String content;
    @ManyToOne
    CreatorProfile creator;
    @OneToOne
    Post post;

    @Builder
    public Reply(String content, CreatorProfile creator, Post post) {
        this.content = content;
        this.creator = creator;
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }
}
