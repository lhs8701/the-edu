package joeuncamp.dabombackend.domain.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.dto.EnrollDto;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.course.service.MyCourseService;
import joeuncamp.dabombackend.domain.wish.dto.WishDto;
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

@Tag(name = "[3-2.MyCourse]", description = "나의 클래스 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyCourseController {
    private final WishService wishService;
    private final EnrollService enrollService;
    private final MyCourseService myCourseService;

    @Operation(summary = "강좌 수강 등록 여부를 조회합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/enroll/courses/{courseId}")
    public ResponseEntity<Boolean> doesEnrolled(@PathVariable Long courseId, @AuthenticationPrincipal Member member) {
        EnrollDto.Request requestDto = new EnrollDto.Request(member.getId(), courseId);
        Boolean response = enrollService.doesEnrolled(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "찜을 하거나, 해제합니다.", description = "이미 찜이 되어있는 경우, 해제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/wish/courses/{courseId}")
    public ResponseEntity<Void> toggleWish(@PathVariable Long courseId, @AuthenticationPrincipal Member member) {
        WishDto.Request requestDto = new WishDto.Request(member.getId(), courseId);
        wishService.toggleWish(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "찜한 강좌인지 확인합니다.", description = "찜이 되어있는 경우, true를 반환합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/wish/courses/{courseId}")
    public ResponseEntity<Boolean> checkWish(@PathVariable Long courseId, @AuthenticationPrincipal Member member) {
        WishDto.Request requestDto = new WishDto.Request(member.getId(), courseId);
        Boolean response = wishService.checkWish(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "회원이 찜한 모든 강좌를 조회합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/wish/courses")
    public ResponseEntity<List<CourseDto.ShortResponse>> getWishedCourses(@AuthenticationPrincipal Member member) {
        List<CourseDto.ShortResponse> responseDto = myCourseService.getWishedCourses(member.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "시청 완료한 강좌 목록을 확인합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/courses/completed")
    public ResponseEntity<List<CourseDto.StatusResponse>> getCompletedCourses(@AuthenticationPrincipal Member member) {
        List<CourseDto.StatusResponse> responseDto = myCourseService.getCompletedCourses(member.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "시청 중인 강좌 목록을 확인합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/courses/ongoing")
    public ResponseEntity<List<CourseDto.StatusResponse>> getOngoingCourses(@AuthenticationPrincipal Member member) {
        List<CourseDto.StatusResponse> responseDto = myCourseService.getOngoingCourses(member.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "최근 시청한 강좌 조회", description = "최근에 시청했던 강좌 3개를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/courses/recent")
    public ResponseEntity<List<CourseDto.StatusResponse>> getRecentPlayedCourses(@AuthenticationPrincipal Member member) {
        List<CourseDto.StatusResponse> responseDto = myCourseService.getRecentPlayedCourses(member.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
