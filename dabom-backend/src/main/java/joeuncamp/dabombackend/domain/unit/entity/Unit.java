package joeuncamp.dabombackend.domain.unit.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Unit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    int sequence;
    String title;
    String description;

    @ManyToOne
    @JoinColumn
    Course course;

    @Builder
    public Unit(int sequence, String title, String description, Course course){
        setCourse(course);
        this.sequence = sequence;
        this.title = title;
        this.description = description;
    }
    private void setCourse(Course course){
        if (this.course != null){
            course.getUnitList().remove(this);
        }
        this.course = course;
        course.getUnitList().add(this);
    }
}
