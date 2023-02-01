package joeuncamp.dabombackend.domain.file;

import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class FileUtil {
    final static String DELIMITER = "\\";

    public static File createFromMultipart(MultipartFile multipartFile) {
        try {
            String path = System.getProperty("user.dir");
            File convertedFile = new File(path + DELIMITER + Objects.requireNonNull(multipartFile.getOriginalFilename()));
            multipartFile.transferTo(convertedFile);
            return convertedFile;
        }catch (IOException e) {
            log.info(e.getMessage());
            throw new CInternalServerException();
        }
    }
}
