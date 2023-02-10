package joeuncamp.dabombackend.domain.order.entity.price;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.order.entity.CoursePeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    long price;
    @Enumerated(value = EnumType.STRING)
    CoursePeriod coursePeriod;
    @ManyToOne
    @JoinColumn
    Course course;

    public void update(long price) {
        this.price = price;
    }

    @Builder
    public Ticket(Course course, CoursePeriod coursePeriod){
        this.price = coursePeriod.getDefaultPrice();
        this.coursePeriod = coursePeriod;
        this.course = course;
    }
}
