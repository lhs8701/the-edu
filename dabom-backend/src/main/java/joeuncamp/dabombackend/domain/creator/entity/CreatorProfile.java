package joeuncamp.dabombackend.domain.creator.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
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
    boolean activated;

    @OneToMany(mappedBy = "creatorProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    List<Course> uploadedCourses = new ArrayList<>();

    public void activate(){
        this.activated = true;
    }
}
