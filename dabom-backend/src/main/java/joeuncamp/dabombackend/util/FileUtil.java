package joeuncamp.dabombackend.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Component
public class FileUtil {
    final static String DELIMITER = "\\";

    public static File createFromMultipart(MultipartFile multipartFile) throws IOException {
        String path = System.getProperty("user.dir");
        File convertedFile = new File(path + DELIMITER + Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(convertedFile);
        return convertedFile;
    }
}
