package joeuncamp.dabombackend.domain.file.video.service;

import joeuncamp.dabombackend.domain.file.FileUtil;
import joeuncamp.dabombackend.domain.file.video.entity.VideoInfo;
import joeuncamp.dabombackend.util.hls.service.HlsConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final HlsConvertor hlsConvertor;

    /**
     * 동영상을 m3u8확장자로 변환하여 저장합니다.
     * @param multipartFile multipartFile
     * @return 비디오 정보
     */
    public VideoInfo saveVideo(MultipartFile multipartFile){
        File originalFile = FileUtil.createFromMultipart(multipartFile);
        return hlsConvertor.convertToM3u8(originalFile);
    }
}
