package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.global.constant.ImageSize;
import org.springframework.stereotype.Service;

@Service
public class ImageUtil {

    public static String makeFileName(String fileName, ImageSize imageSize) {
        int index = fileName.indexOf(".");
        String extension = fileName.substring(index + 1);
        String nameOnly = fileName.substring(0, index);
        return String.format("%s_%s.%s", nameOnly, imageSize.getPostFix(), extension);
    }
}
