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

    String DELIMITER = "\\";
    private final ImageConvertor imageConvertor;
    private final ImageUploader imageUploader;
    private final ImageResizer imageResizer;

    public ImageInfo save(MultipartFile multipartFile, ImageSize imageSize) {
        try {
            File originalFile = saveToTemporaryStorage(multipartFile);
            imageConvertor.convertImage(originalFile);
            File resizedFile = imageResizer.resize(originalFile, imageSize);
            ImageInfo imageInfo = imageUploader.upload(resizedFile);
            imageUploader.delete(originalFile);
            imageUploader.delete(resizedFile);
            return imageInfo;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IO Exception 발생");
            throw new CInternalServerException();
        }
    }

    private File saveToTemporaryStorage(MultipartFile multipartFile) {
        File convertedFile = new File(System.getProperty("user.dir") + DELIMITER + Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            multipartFile.transferTo(convertedFile);
        } catch (IOException e) {
            throw new CInternalServerException();
        }
        return convertedFile;
    }

    public ImageInfo update(MultipartFile file) {
        return null;
    }

    public void delete(ImageInfo imageInfo) {

    }
}
