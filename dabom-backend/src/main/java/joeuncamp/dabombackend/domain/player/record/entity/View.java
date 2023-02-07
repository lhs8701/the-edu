package joeuncamp.dabombackend.domain.player.record.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class View extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn
    Member member;

    @ManyToOne
    @JoinColumn
    Unit unit;
}
