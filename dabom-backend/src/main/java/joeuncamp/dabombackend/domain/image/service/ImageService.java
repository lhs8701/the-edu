package joeuncamp.dabombackend.domain.image.service;

import joeuncamp.dabombackend.domain.image.entity.ImageInfo;
import joeuncamp.dabombackend.global.constant.ImageSize;
import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    @Value("${path.images}")
    String IMAGE_STORAGE_PATH;
    String DELIMITER = "\\";
    private final ImageConvertor imageConvertor;
    private final ImageResizer imageResizer;
    private final ImageUploader imageUploader;
    private final ImageUtil imageUtil;

    /**
     * 이미지 파일을 저장소에 저장합니다.
     *
     * @param multipartFile 멀티파트파일
     * @return 이미지 정보
     */
    public ImageInfo saveImage(MultipartFile multipartFile) {
        try {
            File localFileOriginal = createFileFromMultipart(multipartFile);
            imageConvertor.convertImage(localFileOriginal);

            File localFileSmall = imageResizer.createResizedFile(localFileOriginal, ImageSize.SMALL);
            File localFileMedium = imageResizer.createResizedFile(localFileOriginal, ImageSize.MEDIUM);

            String remotePathSmall = getRemoteFilePath(localFileSmall);
            String remotePathMedium = getRemoteFilePath(localFileMedium);
            String remotePathOriginal = getRemoteFilePath(localFileOriginal);
            imageUploader.uploadFile(localFileSmall, remotePathSmall);
            imageUploader.uploadFile(localFileMedium, remotePathMedium);
            imageUploader.uploadFile(localFileOriginal, remotePathOriginal);

            imageUploader.delete(localFileSmall);
            imageUploader.delete(localFileMedium);
            imageUploader.delete(localFileOriginal);
            return ImageInfo.builder()
                    .smallFilePath(remotePathSmall)
                    .mediumFilePath(remotePathMedium)
                    .originalFilePath(remotePathOriginal)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new CInternalServerException();
        }
    }

    private File createFileFromMultipart(MultipartFile multipartFile) throws IOException {
        String path = System.getProperty("user.dir");
        File convertedFile = new File(path + DELIMITER + Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(convertedFile);
        return convertedFile;
    }

    private String getRemoteFilePath(File file) {
        return IMAGE_STORAGE_PATH + DELIMITER + file.getName();
    }

    /**
     * 이미지를 삭제합니다.
     * 해당 이미지의 모든 사이즈 버전이 일괄 삭제됩니다.
     *
     * @param imageInfo 이미지 정보
     * @throws IOException
     */
    public void delete(ImageInfo imageInfo) throws IOException {
        File fileSmall = new File(imageInfo.getSmallFilePath());
        File fileMedium = new File(imageInfo.getMediumFilePath());
        File fileOriginal = new File(imageInfo.getOriginalFilePath());
        imageUploader.delete(fileSmall);
        imageUploader.delete(fileMedium);
        imageUploader.delete(fileOriginal);
    }
}
