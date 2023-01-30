package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.domain.image.entity.ImageInfo;
import joeuncamp.dabombackend.global.constant.ImageSize;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {
    String TEMP_STORAGE_URL = "E:\\ROOM\\Github\\dabom\\dabom-backend\\src\\test\\resources\\temp";
    ImageConvertor imageConvertor;
    ImageUploader imageUploader;
    ImageResizer imageResizer;

    public ImageInfo save(MultipartFile multipartFile) throws IOException {
        File tempFile = toFile(multipartFile);
        tempFile = imageConvertor.convertImage(tempFile);
        tempFile = imageResizer.resize(tempFile, ImageSize.MEDIUM);
        File created = imageUploader.upload(tempFile);
        imageUploader.delete(tempFile);
        return new ImageInfo(created);
    }

    private File toFile(MultipartFile multipartFile) {
        File convertedFile = new File(TEMP_STORAGE_URL + "\\" + Objects.requireNonNull(multipartFile.getOriginalFilename()));
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
