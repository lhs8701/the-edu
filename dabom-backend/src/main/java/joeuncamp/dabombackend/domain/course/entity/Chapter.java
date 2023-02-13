package joeuncamp.dabombackend.domain.course.entity;


import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    int sequence;
    Long courseId;
    boolean isDefault;
    @Builder.Default
    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    List<Unit> units = new ArrayList<>();;

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", sequence=" + sequence +
                '}';
    }
}
