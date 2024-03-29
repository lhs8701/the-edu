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
    int score;

    @Builder
    public Review(Member member, Course course, String content, int score){
        setMember(member);
        setCourse(course);
        this.content = content;
        this.score = score;
        this.likes = 0;
    }

    @Override
    public String toString() {
        return "Review{" +
                "score=" + score +
                '}';
    }

    public void update(String content, int score) {
        this.content = content;
        this.score = score;
    }
}
