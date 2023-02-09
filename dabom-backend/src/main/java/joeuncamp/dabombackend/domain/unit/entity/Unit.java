package joeuncamp.dabombackend.domain.unit.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Chapter;
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
    String title;
    String description;

    @ManyToOne
    @JoinColumn
    Course course;

    @ManyToOne
    @JoinColumn
    Chapter chapter;
    VideoInfo videoInfo;

    @Builder
    public Unit(int sequence, String title, String description, Course course, VideoInfo videoInfo, Chapter chapter){
        this.sequence = sequence;
        this.title = title;
        this.description = description;
        this.videoInfo = videoInfo;
        this.chapter = chapter;
        setCourse(course);
    }
    private void setCourse(Course course){
        if (this.course != null){
            course.getUnitList().remove(this);
        }
        this.course = course;
        course.getUnitList().add(this);
    }

    public void setSequence(int sequence){
        this.sequence = sequence;
    }

    public void setChapter(Chapter chapter) {
        if (this.chapter!=null){
            this.chapter.getUnits().remove(this);
        }
        this.chapter = chapter;
        chapter.getUnits().add(this);
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", sequence=" + sequence +
                ", title='" + title + '\'' +
                ", course=" + course +
                ", chapter=" + chapter +
                '}';
    }
}
