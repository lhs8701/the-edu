package joeuncamp.dabombackend.domain.file.image.service;

import joeuncamp.dabombackend.domain.file.FileUtil;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.ImageSize;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final ImageConvertor imageConvertor;
    private final ImageResizer imageResizer;
    private final ImageUploader imageUploader;

    /**
     * 이미지 파일을 저장소에 저장합니다.
     *
     * @param multipartFile 멀티파트파일
     * @return 이미지 정보
     */
    public ImageInfo saveImage(MultipartFile multipartFile) {
        try {
            File localFileOriginal = FileUtil.createFromMultipart(multipartFile);
            imageConvertor.convertImage(localFileOriginal);

            File localFileSmall = imageResizer.createResizedFile(localFileOriginal, ImageSize.SMALL);
            File localFileMedium = imageResizer.createResizedFile(localFileOriginal, ImageSize.MEDIUM);

            String smallFilePath = imageUploader.uploadImage(localFileSmall);
            String mediumFilePath = imageUploader.uploadImage(localFileMedium);
            String originalFilePath = imageUploader.uploadImage(localFileOriginal);

            imageUploader.delete(localFileSmall);
            imageUploader.delete(localFileMedium);
            imageUploader.delete(localFileOriginal);
            return ImageInfo.builder()
                    .smallFilePath(smallFilePath)
                    .mediumFilePath(mediumFilePath)
                    .originalFilePath(originalFilePath)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new CInternalServerException();
        }
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
