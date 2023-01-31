package joeuncamp.dabombackend.domain.image.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class MultipartDto {
    MultipartFile courseThumbnail;
}
