package joeuncamp.dabombackend.domain.file.image.service;

import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.global.constant.ImageSize;
import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class ImageUtil {

    public static String makeFileName(String fileName, ImageSize imageSize) {
        int index = fileName.indexOf(".");
        String extension = fileName.substring(index + 1);
        String nameOnly = fileName.substring(0, index);
        return String.format("%s_%s.%s", nameOnly, imageSize.getPostFix(), extension);
    }

    public static ImageInfo getImageInfo(String originalImageUrl){
        File originalFile = new File(originalImageUrl);
        String directory = originalFile.getParent();
        if (!originalFile.exists()){
            log.error("해당 파일이 존재하지 않습니다.");
            throw new CIllegalArgumentException();
        }
        return ImageInfo.builder()
                .originalFilePath(originalImageUrl)
                .mediumFilePath(directory + "\\" + ImageUtil.makeFileName(originalFile.getName(), ImageSize.MEDIUM))
                .smallFilePath(directory + "\\" + ImageUtil.makeFileName(originalFile.getName(), ImageSize.SMALL))
                .build();
    }
}
