package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.order.entity.CoursePeriod;
import joeuncamp.dabombackend.domain.order.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("ticket")
@Entity
public class Ticket extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(value = EnumType.STRING)
    CoursePeriod coursePeriod;
    @ManyToOne
    @JoinColumn
    Course course;

    @Builder
    public Ticket(Course course, CoursePeriod coursePeriod) {
        this.productName = course.getTitle() + " " + coursePeriod.getDescription();
        this.productDetail = course.getDescription();
        this.imageInfo = course.getThumbnailImage();
        this.price = new Price(1000L, 1000L);
        this.coursePeriod = coursePeriod;
        this.course = course;
    }

    public void updatePrice(long costPrice, long discountedPrice) {
        this.price.costPrice = costPrice;
        this.price.discountedPrice = discountedPrice;
    }
}
