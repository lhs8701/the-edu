package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.domain.file.image.service.ImageConvertor;
import joeuncamp.dabombackend.domain.file.image.service.ImageService;
import joeuncamp.dabombackend.domain.file.image.service.ImageUploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @InjectMocks
    ImageService imageService;

    @Mock
    ImageUploader imageUploader;

    @Mock
    ImageConvertor imageConvertor;

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
        assertThat(fileName).isEqualTo(FILE_NAME);
    }

//    @Test
//    @DisplayName("이미지 파일을 저장한다.")
//    void 이미지_파일을_저장한다() throws IOException {
//        // given
//        String FILE_NAME = "sample";
//        String CONTENT_TYPE = "jpg";
//        String PATH = "src/test/resources";
//        MockMultipartFile mockMultipartFile = getMockMultipartFile(FILE_NAME, CONTENT_TYPE, PATH + "\\" + "sample.jpg");
//        given(imageUploader.upload(any())).willReturn(new File(PATH + "\\storage\\sample.jpg"));
//
//        // when
//        ImageInfo imageInfo = imageService.save(mockMultipartFile);
//
//        // then
//        assertThat(imageInfo.getFileName()).isEqualTo("sample.jpg");
//    }
}
