package joeuncamp.dabombackend.domain.member.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Creator")
public class Creator extends Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String creatorName;
}
