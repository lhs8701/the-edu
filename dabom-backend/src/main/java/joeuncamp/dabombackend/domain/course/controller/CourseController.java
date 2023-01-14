package joeuncamp.dabombackend.domain.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.dto.*;
import joeuncamp.dabombackend.domain.course.service.CourseService;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.wish.dto.WishRequestDto;
import joeuncamp.dabombackend.domain.wish.service.WishService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "[Course]", description = "클래스와 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CourseController {
    private final CourseService courseService;
    private final EnrollService enrollService;

    private final WishService wishService;

    @Operation(summary = "강좌를 개설합니다.", description = "강좌 개설은 크리에이터 프로필을 활성화한 회원만 가능합니다. \n 개설된 강좌의 아이디넘버가 반환됩니다.")
    @Parameter(name = Header.JWT_HEADER, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses")
    public ResponseEntity<Long> openCourse(@AuthenticationPrincipal Member member, @RequestBody CourseDto.CreationRequest requestDto) {
        Long response = courseService.openCourse(requestDto, member.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(summary = "강좌를 단건 조회합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("permitAll()")
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<CourseDto.Response> getCourse(@PathVariable @NotNull Long courseId) {
        CourseDto.Response responseDto = courseService.getCourse(courseId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "카테고리 내의 전체 강좌를 조회합니다.", description = "정해진 카테고리가 아닐 경우 예외가 반환됩니다.")
    @Parameter(name = Header.JWT_HEADER, description="어세스토큰", required=true, in=ParameterIn.HEADER, example=ExampleValue.JWT.ACCESS)
    @PreAuthorize("permitAll()")
    @GetMapping("/courses/category/{category}")
    public ResponseEntity<List<CourseDto.ShortResponse>> getCourseByCategory(@PathVariable String category){
        List<CourseDto.ShortResponse> responseDto = courseService.getCoursesByCategory(category);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @Operation(summary="강좌에 수강신청합니다.", description="이미 수강신청한 강좌인 경우 예외가 발생합니다.")
    @Parameter(name = Header.JWT_HEADER, description="어세스토큰", required=true, in=ParameterIn.HEADER, example=ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/enroll")
    public ResponseEntity<Void> enroll(@RequestBody EnrollRequestDto requestDto){
        enrollService.enroll(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary="찜을 하거나, 해제합니다.", description="이미 찜이 되어있는 경우, 해제합니다.")
    @Parameter(name = Header.JWT_HEADER, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/wish")
    public ResponseEntity<Void> toggleWish(@RequestBody WishRequestDto wishRequestDto){
        wishService.toggleWish(wishRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
