package joeuncamp.dabombackend.domain.file.image.service;

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

    @Value("${path.root}")
    String ROOT_PATH;

    @Value("${path.images}")
    String IMAGE_PREFIX;

    final String DELIMITER = "\\";

    public String uploadImage(File source) throws IOException {
        Files.copy(Path.of(source.getAbsolutePath()), Path.of(ROOT_PATH + IMAGE_PREFIX + DELIMITER + source.getName()), StandardCopyOption.REPLACE_EXISTING);
        return IMAGE_PREFIX + DELIMITER + source.getName();
    }

    public void delete(File file) throws IOException {
        Files.deleteIfExists(Path.of(file.getAbsolutePath()));
    }
}
