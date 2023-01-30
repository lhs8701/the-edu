package joeuncamp.dabombackend.domain.image.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageInfo {
    String smallFilePath;
    String mediumFilePath;
    String largeFilePath;
    String fileName;
}
