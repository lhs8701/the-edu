package joeuncamp.dabombackend.domain.unit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Builder
public class Unit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    int order;
    String title;
    String description;

    @ManyToOne
    @JoinColumn
    Course course;
}
