package joeuncamp.dabombackend.domain._etc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.image.dto.MultipartDto;
import joeuncamp.dabombackend.domain.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.image.service.ImageService;
import joeuncamp.dabombackend.util.hls.service.HlsConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "[99.Test]")
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {
    private final ImageService imageService;
    private final HlsConvertor hlsConvertor;

    @Operation(summary="개발 서버 사진 업로드 테스트", description="")
    @PreAuthorize("permitAll()")
    @PostMapping("/test/image")
    public ResponseEntity<ImageInfo> uploadImage(@RequestPart MultipartFile multipartFile){
        ImageInfo imageInfo = imageService.saveImage(multipartFile);
        return new ResponseEntity<>(imageInfo, HttpStatus.OK);
    }

    @Operation(summary="사진 여러 장 업로드 테스트", description="")
    @PreAuthorize("permitAll()")
    @PostMapping("/test/image/multiple")
    public ResponseEntity<List<ImageInfo>> uploadImage(@RequestPart List<MultipartFile> multipartFiles){
        List<ImageInfo> list = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            list.add(imageService.saveImage(multipartFile));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @Operation(summary="동영상을 m3u8 포맷으로 변환", description="")
    @PreAuthorize("permitAll()")
    @PostMapping("/test/media")
    public ResponseEntity<?> convertToM3u8(@RequestPart MultipartFile multipartFile){
        hlsConvertor.convertToM3u8(multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
