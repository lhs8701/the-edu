package joeuncamp.dabombackend.domain.event.entity;

import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.event.dto.EventDto;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String content;
    ImageInfo bannerImage;
    LocalDate startDate;
    LocalDate endDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Member writer;

    public void update(EventDto.UpdateRequest updateParam){
        this.title = updateParam.getTitle();
        this.content = updateParam.getContent();
        this.bannerImage = new ImageInfo(updateParam.getBannerImage());
        this.startDate = updateParam.getStartDate();
        this.endDate = updateParam.getEndDate();
    }
}
