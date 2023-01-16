package joeuncamp.dabombackend.domain.post.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("inquiry")
public class Inquiry extends Post{

    @Builder
    public Inquiry(Member member, Course course, String content){
        setMember(member);
        setCourse(course);
        this.likes = 0;
        this.content = content;
    }
}
