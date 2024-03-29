package joeuncamp.dabombackend.domain.file.image.service;

import joeuncamp.dabombackend.domain.file.FileUtil;
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
    public File createResizedFile(File file, ImageSize imageSize) {
        try {
            BufferedImage bufferedImage = resize(file, imageSize);
            String fileName = FileUtil.makeImageFileName(file.getName(), imageSize);
            return bufferedImageToFile(file.getParent() + DELIMITER + fileName, bufferedImage);
        } catch (IOException e) {
            throw new CInternalServerException();
        }
    }

    private BufferedImage resize(File file, ImageSize imageSize) throws IOException {
        BufferedImage originalBufferedImage = ImageIO.read(file);
        int width = originalBufferedImage.getWidth();
        int height = originalBufferedImage.getHeight();
        int resizedWidth = (int) (width * imageSize.getRatio());
        int resizedHeight = (int) (height * imageSize.getRatio());

        final Image image = originalBufferedImage.getScaledInstance(resizedWidth, resizedHeight, Image.SCALE_SMOOTH);
        BufferedImage newBufferedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = newBufferedImage.getGraphics();
        graphics.drawImage(image, 0, 0, Color.WHITE, null);
        return newBufferedImage;
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
