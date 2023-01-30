package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageConvertor {
    private final Tika tika;
    public File convertImage(File file) {
        try (ImageOutputStream stream = new FileImageOutputStream(file)) {
            String mimeType = tika.detect(file);
            ImageWriter imageWriter = ImageIO.getImageWritersByMIMEType(mimeType).next();
            imageWriter.setOutput(stream);
            final BufferedImage image = ImageIO.read(file);
            IIOImage iioImage = new IIOImage(image, null, null);
            imageWriter.write(null, iioImage, setImageWriteParam());
            imageWriter.dispose();
            return file;
        } catch (IOException e) {
            log.error("이미지파일 변환 중 오류 발생");
            throw new CInternalServerException();
        }
    }

    private ImageWriteParam setImageWriteParam() {
        JPEGImageWriteParam imageWriteParam = new JPEGImageWriteParam(Locale.KOREA);
        imageWriteParam.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
        return imageWriteParam;
    }
}
