package joeuncamp.dabombackend.domain.course.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import joeuncamp.dabombackend.global.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn
    CreatorProfile creatorProfile;

    public void setCreatorProfile(CreatorProfile creatorProfile) {
        if (this.creatorProfile != null){
            this.creatorProfile.getUploadedCourses().remove(this);
        }
        this.creatorProfile = creatorProfile;
        creatorProfile.getUploadedCourses().add(this);
    }
}
