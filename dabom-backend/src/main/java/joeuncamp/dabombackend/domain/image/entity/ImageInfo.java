package joeuncamp.dabombackend.domain.image.entity;

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
    String fileName;
    String filePath;
}
