package joeuncamp.dabombackend.domain.order.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.global.constant.ChargeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("ticket")
@Entity
public class Ticket extends Item {
    @Enumerated(value = EnumType.STRING)
    ChargeType chargeType;
    @Enumerated(value = EnumType.STRING)
    CoursePeriod coursePeriod;
    @ManyToOne
    @JoinColumn
    Course course;

    @Builder
    public Ticket(long costPrice, long discountedPrice, CoursePeriod coursePeriod, Course course, ChargeType chargeType) {
        this.productName = course.getTitle() + " " + coursePeriod.getDescription();
        this.productDetail = course.getDescription();
        this.price = new Price(costPrice, discountedPrice);
        this.coursePeriod = coursePeriod;
        this.course = course;
        this.image = course.getThumbnailImage();
        this.itemType = ItemType.TICKET;
        this.chargeType = chargeType;
    }

    public static Ticket newFreeTicket(Course course) {
        return Ticket.builder()
                .costPrice(0)
                .discountedPrice(0)
                .course(course)
                .coursePeriod(CoursePeriod.UNLIMITED)
                .chargeType(ChargeType.FREE)
                .build();
    }

    public static Ticket newPaidTicket(Course course, CoursePeriod coursePeriod) {
        return Ticket.builder()
                .costPrice(1000)
                .discountedPrice(1000)
                .course(course)
                .coursePeriod(coursePeriod)
                .chargeType(ChargeType.PAID)
                .build();
    }

    public void updatePrice(long costPrice, long discountedPrice) {
        this.price.costPrice = costPrice;
        this.price.discountedPrice = discountedPrice;
    }
}
