package joeuncamp.dabombackend.domain.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Student")
public class Student extends Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String studentName;
}
