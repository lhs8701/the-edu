package joeuncamp.dabombackend.domain.member.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    String creatorNickname;

    @OneToOne(mappedBy = "creatorProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Member member;

    @OneToMany
    List<Course> uploadedCourses;
}
