package joeuncamp.dabombackend.domain.course.entity;

import jakarta.persistence.*;
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

    public RankedCourse(Course course){
        this.id = course.getId();
        this.title = course.getTitle();
        this.instructor = course.getInstructorName();
        this.category = course.getCategory();
    }
}
