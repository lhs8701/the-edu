package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.domain.image.entity.ImageInfo;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {

    ImageConverter imageConverter;

    public ImageInfo save(MultipartFile multipartFile){
        File file = toFile(multipartFile);
        imageConverter.convertImage(file);
        return new ImageInfo();
    }

    private File toFile(MultipartFile multipartFile) {
        File convertedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            multipartFile.transferTo(convertedFile);
        } catch (IOException e) {
            throw new CInternalServerException();
        }
        return convertedFile;
    }

    public ImageInfo update(MultipartFile file){
        return null;
    }

    public void delete(ImageInfo imageInfo){

    }
}
