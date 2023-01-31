package joeuncamp.dabombackend.domain.image.service;

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
    String IMAGE_STORAGE_URL = "E:\\ROOM\\Github\\dabom\\dabom-backend\\src\\test\\resources\\storage";
    String DELIMITER = "\\";

    public File upload(File file) throws IOException {
        log.info(System.getProperty("user.dir"));
        Path path = Files.copy(file.toPath(), Path.of(IMAGE_STORAGE_URL + DELIMITER + file.getName()), StandardCopyOption.REPLACE_EXISTING);
        return new File(path.toUri());
    }

    public void delete(File file) throws IOException {
        Files.delete(file.toPath());
    }
}
