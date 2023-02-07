package joeuncamp.dabombackend.domain.player.feedback.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String comment;
    @ManyToOne
    @JoinColumn
    Member member;
    @ManyToOne
    @JoinColumn
    Unit unit;
    boolean thumbsUp;

    public Feedback(Member member, Unit unit) {
        this.member = member;
        this.unit = unit;
    }

    public void update(String comment, Boolean thumbsUp) {
        this.comment = comment;
        this.thumbsUp = thumbsUp;
    }
}
