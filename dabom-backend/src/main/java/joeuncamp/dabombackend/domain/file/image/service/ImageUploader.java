package joeuncamp.dabombackend.domain.file.image.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class ImageUploader {

    public void uploadFile(File file, String destination) throws IOException {
        Files.copy(Path.of(file.getAbsolutePath()), Path.of(destination), StandardCopyOption.REPLACE_EXISTING);
    }

    public void delete(File file) throws IOException {
        Files.deleteIfExists(Path.of(file.getAbsolutePath()));
    }
}
