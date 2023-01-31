package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.domain.image.entity.ImageInfo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = { "path.images=E:\\ROOM\\Github\\dabom\\dabom-backend\\src\\test\\resources\\images" })
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ImageUploaderTest {

    @InjectMocks
    ImageUploader imageUploader;

    @Test
    @Disabled
    @DisplayName("지정된 위치에 이미지파일을 생성한다.")
    void 지정된_위치에_이미지파일을_생성한다() throws IOException {
        // given
        File file = new File("src/test/resources/sample.jpg");

        // when
        ImageInfo imageInfo = imageUploader.upload(file);

        // then
        assertThat(imageInfo.getFileName()).isEqualTo("sample.jpg");
    }
}
