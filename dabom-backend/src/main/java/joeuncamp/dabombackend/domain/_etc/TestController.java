package joeuncamp.dabombackend.domain._etc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.file.FileUtil;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.file.image.service.ImageService;
import joeuncamp.dabombackend.domain.order.service.PostOrderManager;
import joeuncamp.dabombackend.util.hls.service.HlsConvertor;
import joeuncamp.dabombackend.util.tossapi.TossService;
import joeuncamp.dabombackend.util.tossapi.dto.AuthResultResponse;
import joeuncamp.dabombackend.util.tossapi.dto.TxIdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "[99.Test]")
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class TestController {
    private final ImageService imageService;
    private final HlsConvertor hlsConvertor;
    private final TossService tossService;
    private final List<PostOrderManager> postOrderManager;

    @Operation(summary = "개발 서버 사진 업로드 테스트", description = "")
    @PreAuthorize("permitAll()")
    @PostMapping("/test/image")
    public ResponseEntity<ImageInfo> uploadImage(@RequestPart MultipartFile multipartFile) {
        ImageInfo imageInfo = imageService.saveImage(multipartFile);
        return new ResponseEntity<>(imageInfo, HttpStatus.OK);
    }

    @Operation(summary = "사진 여러 장 업로드 테스트", description = "")
    @PreAuthorize("permitAll()")
    @PostMapping("/test/image/multiple")
    public ResponseEntity<List<ImageInfo>> uploadImage(@RequestPart List<MultipartFile> multipartFiles) {
        List<ImageInfo> list = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            list.add(imageService.saveImage(multipartFile));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @Operation(summary = "동영상을 m3u8 포맷으로 변환", description = "")
    @PreAuthorize("permitAll()")
    @PostMapping("/test/media")
    public ResponseEntity<?> convertToM3u8(@RequestPart MultipartFile multipartFile) {
        File file = FileUtil.createFromMultipart(multipartFile);
        hlsConvertor.convertToM3u8(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "토스 토큰 발급 테스트", description = "")
    @PreAuthorize("permitAll()")
    @PostMapping("/test/toss-token")
    public ResponseEntity<?> issueToken() {
        String accessToken = tossService.issueToken();
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @Operation(summary = "토스 txId 발급 테스트", description = "")
    @PreAuthorize("permitAll()")
    @PostMapping("/test/toss-txId/{accessToken}")
    public ResponseEntity<?> issueTxId(@PathVariable String accessToken) {
        TxIdResponse response = tossService.issueTxId(accessToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "토스 txId 발급 테스트", description = "")
    @PreAuthorize("permitAll()")
    @PostMapping("/test/toss-result/{accessToken}/{txid}")
    public ResponseEntity<?> getResult(@PathVariable String accessToken, @PathVariable String txid) {
        AuthResultResponse response = tossService.getAuthResult(accessToken, txid);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
