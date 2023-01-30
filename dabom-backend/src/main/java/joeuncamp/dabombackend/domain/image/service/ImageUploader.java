package joeuncamp.dabombackend.domain.image.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ImageUploader {

    String IMAGE_STORAGE_URL = "E:\\ROOM\\Github\\dabom\\dabom-backend\\src\\test\\resources\\storage";
    String DELIMITER = "\\";

    public File upload(File file) throws IOException {
        Path path = Files.copy(file.toPath(), Path.of(IMAGE_STORAGE_URL + DELIMITER + file.getName()), StandardCopyOption.REPLACE_EXISTING);
        return new File(path.toUri());
    }

    public void delete(File file) throws IOException {
        Files.delete(file.toPath());
    }
}
