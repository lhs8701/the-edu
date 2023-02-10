package joeuncamp.dabombackend.domain.player.feedback.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn
    Member member;
    @ManyToOne
    @JoinColumn
    Unit unit;
    boolean thumbsUp;
    boolean thumbsDown;

    public Feedback(Member member, Unit unit) {
        this.member = member;
        this.unit = unit;
    }

    public void update(boolean thumbsUp, boolean thumbsDown) {
        if (thumbsUp && thumbsDown) {
            this.thumbsUp = false;
            this.thumbsDown = false;
            return;
        }
        this.thumbsUp = thumbsUp;
        this.thumbsDown = thumbsDown;
    }
}
