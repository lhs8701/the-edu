package joeuncamp.dabombackend.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
public class CreatorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String creatorNickname;

    @OneToOne(mappedBy = "creatorProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Member member;
}
