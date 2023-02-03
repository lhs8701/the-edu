package joeuncamp.dabombackend.domain.course.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.post.entity.Post;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
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

    double averageScore;

    ImageInfo thumbnailImage;

    @ElementCollection
    @CollectionTable(name="description_image", joinColumns = @JoinColumn(name= "description_image_id", referencedColumnName = "id"))
    List<ImageInfo> descriptionImages;

    @ManyToOne
    @JoinColumn(name = "creator_profile_id")
    CreatorProfile creatorProfile;

    @Builder.Default
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Enroll> enrollList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Wish> wishList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Post> postList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Unit> unitList = new ArrayList<>();

    public void setCreatorProfile(CreatorProfile creatorProfile) {
        if (this.creatorProfile != null) {
            this.creatorProfile.getUploadedCourses().remove(this);
        }
        this.creatorProfile = creatorProfile;
        creatorProfile.getUploadedCourses().add(this);
    }

    public String getInstructorName() {
        return this.creatorProfile.getMember().getNickname();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category=" + category +
                '}';
    }
}
