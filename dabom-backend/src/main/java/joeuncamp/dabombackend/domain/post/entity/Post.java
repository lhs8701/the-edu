package joeuncamp.dabombackend.domain.post.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Getter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@DynamicInsert
public abstract class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String content;
    Integer likes;

    @ManyToOne
    @JoinColumn
    Member member;

    @ManyToOne
    @JoinColumn
    Course course;

    void setMember(Member member){
        if (this.member != null){
            this.member.getPostList().remove(this);
        }
        this.member = member;
        member.getPostList().add(this);
    }
    void setCourse(Course course){
        if(this.course != null){
            this.course.getPostList().remove(this);
        }
        this.course = course;
        course.getPostList().add(this);
    }
}
