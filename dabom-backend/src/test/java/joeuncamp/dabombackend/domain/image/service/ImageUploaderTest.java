package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.domain.image.entity.ImageInfo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
