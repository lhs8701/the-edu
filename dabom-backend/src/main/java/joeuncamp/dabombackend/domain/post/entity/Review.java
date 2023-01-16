package joeuncamp.dabombackend.domain.post.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("review")
@Getter
public class Review extends Post{
    int rating;
    int likes;

    @Builder
    public Review(Member member, Course course, String content, int rating, int likes){
        setMember(member);
        setCourse(course);
        this.content = content;
        this.rating = rating;
        this.likes = likes;
    }
}
