package joeuncamp.dabombackend.domain.image.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ImageUploaderTest {

    @InjectMocks
    ImageUploader imageUploader;

    @Test
    @DisplayName("지정된 위치에 이미지파일을 생성한다.")
    void 지정된_위치에_이미지파일을_생성한다() throws IOException {
        // given
        String IMAGE_STORAGE_URL = "E:\\ROOM\\Github\\dabom\\dabom-backend\\src\\test\\resources\\storage";
        File file = new File("src/test/resources/sample.jpg");
        // when
        File created = imageUploader.upload(file);

        // then
        assertThat(created.exists()).isEqualTo(true);
    }
}
