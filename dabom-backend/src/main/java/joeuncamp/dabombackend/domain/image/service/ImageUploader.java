package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.domain.image.entity.ImageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@Slf4j
public class ImageUploader {

    @Value("${path.images}")
    String IMAGE_STORAGE_PATH;
    String DELIMITER = "\\";

    public File uploadMultipartFile(MultipartFile multipartFile) throws IOException{
        String path = System.getProperty("user.dir");
        File convertedFile = new File(path + DELIMITER + Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(convertedFile);
        return convertedFile;
    }
    public ImageInfo uploadFile(File file) throws IOException {
        log.info(System.getProperty("user.dir"));
        log.info("파일 업로드");
        log.info(file.toPath().toString());
        log.info(IMAGE_STORAGE_PATH + DELIMITER + file.getName());
        Files.copy(Path.of(file.getAbsolutePath()), Path.of(IMAGE_STORAGE_PATH + DELIMITER + file.getName()), StandardCopyOption.REPLACE_EXISTING);
        return new ImageInfo(file.getName(), file.getAbsolutePath());
    }

    public void delete(File file) throws IOException {
        log.info("파일 삭제");
        Files.deleteIfExists(Path.of(file.getAbsolutePath()));
    }
}
