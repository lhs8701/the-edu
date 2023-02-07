package joeuncamp.dabombackend.domain.file.video.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class VideoInfo {
    String filePath;
}
