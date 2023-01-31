package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.domain.image.entity.ImageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class ImageUploader {

    @Value("${path.images}")
    String IMAGE_STORAGE_URL;
    String DELIMITER = "\\";

    public ImageInfo upload(File file) throws IOException {
        log.info(System.getProperty("user.dir"));
        log.info("파일 업로드");
        Files.copy(file.toPath(), Path.of(IMAGE_STORAGE_URL + DELIMITER + file.getName()), StandardCopyOption.REPLACE_EXISTING);
        return new ImageInfo(file.getName(), file.getAbsolutePath());
    }

    public void delete(File file) throws IOException {
        log.info("파일 삭제");
        Files.deleteIfExists(Path.of(file.getAbsolutePath()));
    }
}
