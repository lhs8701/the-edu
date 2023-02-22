package joeuncamp.dabombackend.domain._etc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.file.FileUtil;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.file.image.service.ImageService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.OrderJpaRepository;
import joeuncamp.dabombackend.domain.player.record.repository.ViewJpaRepository;
import joeuncamp.dabombackend.domain.player.record.service.ViewChecker;
import joeuncamp.dabombackend.domain.post.repository.ReviewJpaRepository;
import joeuncamp.dabombackend.util.hls.service.HlsConvertor;
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
import java.util.stream.Collectors;

@RestController
@Tag(name = "[99.Test]")
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class TestController {
    private final ImageService imageService;
    private final HlsConvertor hlsConvertor;
    private final OrderJpaRepository orderJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final EnrollJpaRepository enrollJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;
    private final ViewChecker viewChecker;


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
}
