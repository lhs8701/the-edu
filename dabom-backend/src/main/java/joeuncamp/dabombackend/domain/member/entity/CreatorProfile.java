package joeuncamp.dabombackend.domain.member.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
public class CreatorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    Member member;

    @OneToMany(mappedBy = "creatorProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
<<<<<<< HEAD
    @Builder.Default
    List<Course> uploadedCourses = new ArrayList<>();
=======
    List<Course> uploadedCourses;
>>>>>>> 7a661f3b621864659a0047516d34635d7490d7e8
}
