package joeuncamp.dabombackend.domain.unit.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.file.video.entity.VideoInfo;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Unit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    int sequence;

    Long chapterId;
    String title;
    String description;

    @ManyToOne
    @JoinColumn
    Course course;

    VideoInfo videoInfo;

    @Builder
    public Unit(int sequence, String title, String description, Course course, VideoInfo videoInfo){
        this.sequence = sequence;
        this.title = title;
        this.description = description;
        this.videoInfo = videoInfo;
        setCourse(course);
    }
    private void setCourse(Course course){
        if (this.course != null){
            course.getUnitList().remove(this);
        }
        this.course = course;
        course.getUnitList().add(this);
    }
}
