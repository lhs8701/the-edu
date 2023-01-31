package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.global.constant.ImageSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

@ExtendWith(MockitoExtension.class)
public class ImageResizerTest {
    @InjectMocks
    ImageResizer imageResizer;

    @Test
    @DisplayName("")
    void test() {
        // given
        File file = new File("src/test/resources/sample.jpg");
        imageResizer.createResizedFile(file, ImageSize.MEDIUM);
        // when

        // then
    }
}
