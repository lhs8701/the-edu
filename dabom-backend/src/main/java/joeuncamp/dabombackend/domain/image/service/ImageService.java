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

    @Value("${path.root}")
    String ROOT_PATH;
    String DELIMITER = "\\";
    private final ImageConvertor imageConvertor;
    private final ImageUploader imageUploader;
    private final ImageResizer imageResizer;

    public ImageInfo save(MultipartFile multipartFile, ImageSize imageSize) {
        try {
            File originalFile = saveToTemporaryStorage(multipartFile);
            log.info("1"+originalFile.getAbsolutePath());
            imageConvertor.convertImage(originalFile);
            log.info("2"+originalFile.getAbsolutePath());
            File resizedFile = imageResizer.resize(originalFile, imageSize);
            log.info("3"+resizedFile.getAbsolutePath());
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
        File convertedFile = new File(ROOT_PATH + DELIMITER + Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            multipartFile.transferTo(convertedFile);
        } catch (IOException e) {
            e.printStackTrace();
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
