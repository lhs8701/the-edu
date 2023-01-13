package joeuncamp.dabombackend.domain.wish.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Wish extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "courseId")
    Course course;

    @ManyToOne
    @JoinColumn(name = "memberId")
    Member member;

    @Builder
    public Wish(Member member, Course course){
        this.member = member;
        member.getWishList().add(this);

        this.course = course;
        course.getWishList().add(this);
    }
}
