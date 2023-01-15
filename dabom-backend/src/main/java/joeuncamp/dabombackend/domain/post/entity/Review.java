package joeuncamp.dabombackend.domain.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("review")
public class Review extends Post{
    int rating;
    int likes;

    @Builder
    public Review(Member member, Course course, String content, int rating, int likes){
        if (this.member != null){
            this.member.getPostList().remove(this);
        }
        this.member = member;
        member.getPostList().add(this);

        if(this.course != null){
            this.course.getPostList().remove(this);
        }
        this.course = course;
        course.getPostList().add(this);

        this.content = content;
        this.rating = rating;
        this.likes = likes;
    }
}
