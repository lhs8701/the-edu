package joeuncamp.dabombackend.domain.image.service;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import joeuncamp.dabombackend.global.constant.ImageSize;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageResizer {
    final String DELIMITER = "\\";
    public File resize(File file, ImageSize imageSize) {
        try {
            BufferedImage bufferedImage = resizeImage(file, imageSize);
            String fileName = makeFileName(file.getName(), imageSize);
            return bufferedImageToFile(file.getParent() + DELIMITER + fileName, bufferedImage);
        } catch (IOException e) {
            throw new CInternalServerException();
        }
    }

    private BufferedImage resizeImage(File file, ImageSize imageSize) throws IOException {
        BufferedImage originalBufferedImage = ImageIO.read(file);
        final Image image = originalBufferedImage.getScaledInstance(imageSize.getPixelSize(), imageSize.getPixelSize(), Image.SCALE_SMOOTH);
        BufferedImage newBufferedImage = new BufferedImage(imageSize.getPixelSize(), imageSize.getPixelSize(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = newBufferedImage.getGraphics();
        graphics.drawImage(image, 0, 0, Color.WHITE, null);
        return newBufferedImage;
    }

    private String makeFileName(String fileName, ImageSize imageSize) {
        int index = fileName.indexOf(".");
        String extension = fileName.substring(index + 1);
        String nameOnly = fileName.substring(0, index);
        return String.format("%s_%s.%s", nameOnly, imageSize.getAdditionalWord(), extension);
    }

    private File bufferedImageToFile(String fileName, BufferedImage bufferedImage) {
        try {
            File file = new File(fileName);
            ImageIO.write(bufferedImage, "jpg", file);
            return file;
        } catch (IOException e) {
            throw new CInternalServerException();
        }
    }
}
