package joeuncamp.dabombackend.domain.course.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import joeuncamp.dabombackend.global.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String description;

    @Enumerated(value = EnumType.STRING)
    CategoryType category;
    long price;

    @ManyToOne
    @JoinColumn(name = "creator_profile_id")
    CreatorProfile creatorProfile;

    @Builder.Default
    @OneToMany(mappedBy = "course")
    List<Enroll> enrollList = new ArrayList<>();

    public void setCreatorProfile(CreatorProfile creatorProfile) {
        if (this.creatorProfile != null){
            this.creatorProfile.getUploadedCourses().remove(this);
        }
        this.creatorProfile = creatorProfile;
        creatorProfile.getUploadedCourses().add(this);
    }
}
