package joeuncamp.dabombackend.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.dto.ReviewDto;
import joeuncamp.dabombackend.domain.post.service.ReviewService;
import joeuncamp.dabombackend.global.common.IdResponseDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "[Review]", description = "수강 후기 관련 API입니다.")
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary="수강 후기를 작성합니다.", description="")
    @Parameter(name = Header.JWT_HEADER, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/reviews")
    public ResponseEntity<IdResponseDto> writeReview(ReviewDto.Request requestDto){
        IdResponseDto responseDto = reviewService.writeReview(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
