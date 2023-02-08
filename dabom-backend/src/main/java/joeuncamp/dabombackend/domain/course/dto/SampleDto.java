package joeuncamp.dabombackend.domain.course.dto;

import joeuncamp.dabombackend.domain.file.video.entity.VideoInfo;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import lombok.Getter;

@Getter
public class SampleDto {
    String title;
    VideoInfo videoInfo;

    public SampleDto(Unit unit){
        this.title = unit.getTitle();
        this.videoInfo = unit.getVideoInfo();
    }
}
