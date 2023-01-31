package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.domain.image.entity.ImageInfo;
import joeuncamp.dabombackend.global.constant.ImageSize;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final ImageConvertor imageConvertor;
    private final ImageUploader imageUploader;
    private final ImageResizer imageResizer;

    public ImageInfo save(MultipartFile multipartFile, ImageSize imageSize) {
        try {
            File originalFile = imageUploader.uploadMultipartFile(multipartFile);
            log.info("1"+originalFile.getAbsolutePath());
            imageConvertor.convertImage(originalFile);
            log.info("2"+originalFile.getAbsolutePath());
            File resizedFile = imageResizer.resize(originalFile, imageSize);
            log.info("3"+resizedFile.getAbsolutePath());
            ImageInfo imageInfo = imageUploader.uploadFile(resizedFile);
            imageUploader.delete(originalFile);
            imageUploader.delete(resizedFile);
            return imageInfo;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IO Exception 발생");
            throw new CInternalServerException();
        }
    }

    public ImageInfo update(MultipartFile file) {
        return null;
    }

    public void delete(ImageInfo imageInfo) {

    }
}
