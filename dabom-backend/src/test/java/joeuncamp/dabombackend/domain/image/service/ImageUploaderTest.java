package joeuncamp.dabombackend.domain.image.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

//@ContextConfiguration(
//        initializers = {ConfigFileApplicationContextInitializer.class}
//)
@ExtendWith(MockitoExtension.class)
public class ImageUploaderTest {

    @InjectMocks
    ImageUploader imageUploader;

    @Test
    @DisplayName("지정된 위치에 이미지파일을 생성한다.")
    void 지정된_위치에_이미지파일을_생성한다() throws IOException {
        // given
        String path = System.getProperty("user.dir");
        System.out.println("path = " + path);
        File file = new File(path + "\\src\\test\\resources\\sample.jpg");

        // when
        File created = imageUploader.upload(file);

        // then
        assertThat(created.exists()).isEqualTo(true);
    }
}
