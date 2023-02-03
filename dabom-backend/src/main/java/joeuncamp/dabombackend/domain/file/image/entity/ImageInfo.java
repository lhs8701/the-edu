package joeuncamp.dabombackend.domain.file.image.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import joeuncamp.dabombackend.domain.file.FileUtil;
import joeuncamp.dabombackend.global.constant.ImageSize;
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
    @Schema(example = "/static/images/sample_s.jpg")
    String smallFilePath;
    @Schema(example = "/static/images/sample_m.jpg")
    String mediumFilePath;
    @Schema(example = "/static/images/sample.jpg")
    String originalFilePath;

    public ImageInfo(String path){
        this.smallFilePath = FileUtil.makeImageFileName(path, ImageSize.SMALL);
        this.mediumFilePath = FileUtil.makeImageFileName(path, ImageSize.MEDIUM);
        this.originalFilePath = path;
    }
}