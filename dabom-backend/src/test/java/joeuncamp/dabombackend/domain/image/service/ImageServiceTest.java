package joeuncamp.dabombackend.domain.image.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @InjectMocks
    ImageService imageService;

    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }

    @Test
    @DisplayName("MockMultipartFile 동작 테스트")
    void MockMultipartFile의_동작을_테스트한다() throws IOException {
        // given
        String FILE_NAME = "sample";
        String CONTENT_TYPE = "jpg";
        String PATH = "src/test/resources/sample.jpg";

        // when
        MockMultipartFile mockMultipartFile = getMockMultipartFile(FILE_NAME, CONTENT_TYPE, PATH);

        // then
        String originalName = mockMultipartFile.getOriginalFilename();
        String fileName = mockMultipartFile.getName();
        System.out.println("fileName = " + fileName);
        System.out.println("originalName = " + originalName);
        Assertions.assertThat(fileName).isEqualTo(FILE_NAME);
    }

    @Test
    @DisplayName("MultipartFile을 File로 변환한다.")
    void MultipartFile을_File로_변환한다() throws IOException {
        // given
        String FILE_NAME = "sample";
        String CONTENT_TYPE = "jpg";
        String PATH = "src/test/resources/sample.jpg";
        MockMultipartFile mockMultipartFile = getMockMultipartFile(FILE_NAME, CONTENT_TYPE, PATH);

        // when
        imageService.save(mockMultipartFile);

        // then
    }


}
