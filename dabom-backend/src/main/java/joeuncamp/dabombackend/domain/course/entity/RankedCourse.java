package joeuncamp.dabombackend.domain.course.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
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
public class RankedCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String instructor;

    @Enumerated(value = EnumType.STRING)
    CategoryType category;

    ImageInfo thumbnailImage;

    public RankedCourse(Course course){
        this.id = course.getId();
        this.title = course.getTitle();
        this.instructor = course.getInstructorName();
        this.category = course.getCategory();
        this.thumbnailImage = course.getThumbnailImage();
    }
}
