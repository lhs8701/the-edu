package joeuncamp.dabombackend.util.tika;

import org.apache.tika.Tika;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TikaTest {
    @Test
    @DisplayName("jpeg_파일인지_확인한다.")
    void jpeg_파일인지_확인한다() throws IOException {
        // given
        Tika tika = new Tika();
        // when
        String mimeType = tika.detect(new File("src/test/resources/sample.jpg"));
        // then
        assertThat(mimeType).isEqualTo("image/jpeg");
    }

    @Test
    @DisplayName("이미지_파일인지_확인한다.")
    void 이미지_파일인지_확인한다() throws IOException {
        // given
        Tika tika = new Tika();

        List<String> imageTypes = List.of("image/jpeg", "image/pjpeg", "image/png", "image/gif", "image/bmp", "image/x-windows-bmp");

        // when
        String imageMimeType = tika.detect(new File("src/test/resources/sample.jpg"));
        String textMimeType = tika.detect(new File("src/test/resources/sample.txt"));

        // then
        assertThat(imageTypes).contains(imageMimeType);
        assertThat(imageTypes).doesNotContain(textMimeType);
    }
}
