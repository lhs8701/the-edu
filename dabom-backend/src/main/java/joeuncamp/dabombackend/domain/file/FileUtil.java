package joeuncamp.dabombackend.domain.file;

import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.file.video.entity.VideoInfo;
import joeuncamp.dabombackend.global.constant.ImageSize;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class FileUtil {
    final static String DELIMITER = "\\";

    public static File createFromMultipart(MultipartFile multipartFile) {
        try {
            String path = System.getProperty("user.dir");
            File convertedFile = new File(path + DELIMITER + Objects.requireNonNull(multipartFile.getOriginalFilename()));
            multipartFile.transferTo(convertedFile);
            return convertedFile;
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new CInternalServerException();
        }
    }

    public static String makeImageFileName(String fileName, ImageSize imageSize) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1);
        String nameOnly = fileName.substring(0, index);
        return String.format("%s_%s.%s", nameOnly, imageSize.getPostFix(), extension);
    }
}
