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
        }catch (IOException e) {
            log.info(e.getMessage());
            throw new CInternalServerException();
        }
    }

    public static String makeImageFileName(String fileName, ImageSize imageSize) {
        int index = fileName.indexOf(".");
        String extension = fileName.substring(index + 1);
        String nameOnly = fileName.substring(0, index);
        return String.format("%s_%s.%s", nameOnly, imageSize.getPostFix(), extension);
    }

    public static ImageInfo getImageInfo(String imageUrl){
        File originalFile = new File(imageUrl);
        String directory = originalFile.getParent();
        if (!originalFile.exists()){
            log.error("해당 파일이 존재하지 않습니다.");
            throw new CResourceNotFoundException();
        }
        return ImageInfo.builder()
                .originalFilePath(imageUrl)
                .mediumFilePath(directory + DELIMITER + FileUtil.makeImageFileName(originalFile.getName(), ImageSize.MEDIUM))
                .smallFilePath(directory + DELIMITER + FileUtil.makeImageFileName(originalFile.getName(), ImageSize.SMALL))
                .build();
    }

    public static VideoInfo getVideoInfo(String videoUrl){
        File file = new File(videoUrl);
        if (!file.exists()){
            log.error("해당 파일이 존재하지 않습니다.");
            throw new CResourceNotFoundException();
        }
        return new VideoInfo(videoUrl);
    }
}
