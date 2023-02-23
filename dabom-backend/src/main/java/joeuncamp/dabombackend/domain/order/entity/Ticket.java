package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Period;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("ticket")
@Entity
public class Ticket extends Item {
    Integer coursePeriod;
    @OneToOne
    @JoinColumn
    Course course;

    @Builder
    public Ticket(long costPrice, long discountedPrice, Integer coursePeriod, Course course) {
        this.productName = course.getTitle() + " 수강권";
        this.productDetail = course.getDescription();
        this.price = new Price(costPrice, discountedPrice);
        this.coursePeriod = coursePeriod;
        this.course = course;
        this.image = course.getThumbnailImage();
        this.itemType = ItemType.TICKET;
    }

    public static Ticket newFreeTicket(Course course) {
        return Ticket.builder()
                .costPrice(0)
                .discountedPrice(0)
                .course(course)
                .coursePeriod(null)
                .build();
    }

    public static Ticket newPaidTicket(Course course, Integer period) {
        return Ticket.builder()
                .costPrice(1000)
                .discountedPrice(1000)
                .course(course)
                .coursePeriod(period)
                .build();
    }

    public void update(long costPrice, long discountedPrice, Integer coursePeriod) {
        this.price.costPrice = costPrice;
        this.price.discountedPrice = discountedPrice;
        this.coursePeriod = coursePeriod;
    }

    public Period getDuration(){
        if (this.coursePeriod == null){
            return Period.ofYears(1000);
        }
        return Period.ofMonths(this.getCoursePeriod());
    }
}
