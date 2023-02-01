package joeuncamp.dabombackend.domain.file.image.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.File;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ImageInfo {
    String smallFilePath;
    String mediumFilePath;
    String originalFilePath;

    public ImageInfo(String url){
        this.smallFilePath = url;
        this.mediumFilePath = url;
        this.originalFilePath = url;
    }
}
