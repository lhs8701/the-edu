package joeuncamp.dabombackend.domain.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Creator")
public class Creator extends Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String creatorName;
}
