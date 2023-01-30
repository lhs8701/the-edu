package joeuncamp.dabombackend.domain.image.service;

import org.apache.tika.Tika;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ImageConvertorTest {

    @InjectMocks
    ImageConvertor imageConvertor;

    @Mock
    Tika tika;

    @Test
    @DisplayName("")
    void test() throws IOException {
        // given
        File file = new File("src/test/resources/sample.jpg");
        given(tika.detect(file)).willReturn("image/jpeg");

        // when
        imageConvertor.convertImage(file);

        // then
    }
}
