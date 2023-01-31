package joeuncamp.dabombackend.domain.image.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.image.service.ImageService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "[4.Image]", description = "이미지 업로드 관련 API입니다.")
@RequestMapping("/api")
public class ImageController {
    private final ImageService imageService;

    @Operation(summary = "이미지 하나를 업로드합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/images/single")
    public ResponseEntity<ImageInfo> uploadImage(MultipartFile multipartFile) {
        ImageInfo imageInfo = imageService.saveImage(multipartFile);
        return new ResponseEntity<>(imageInfo, HttpStatus.OK);
    }

    @Operation(summary = "이미지 여러개를 업로드합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/images/multiple")
    public ResponseEntity<List<ImageInfo>> uploadImages(List<MultipartFile> multipartFiles) {
        List<ImageInfo> list = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            list.add(imageService.saveImage(multipartFile));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
