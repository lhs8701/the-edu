package joeuncamp.dabombackend.domain.course.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Enroll extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn
    Member member;

    @ManyToOne
    @JoinColumn
    Course course;

    LocalDateTime endDate;

    @Builder
    public Enroll (Member member, Course course){
        this.member = member;
        member.getEnrollList().add(this);
        this.course = course;
        course.getEnrollList().add(this);
    }

    public void setEndDate(LocalDateTime endDate){
        this.endDate = endDate;
    }
}
