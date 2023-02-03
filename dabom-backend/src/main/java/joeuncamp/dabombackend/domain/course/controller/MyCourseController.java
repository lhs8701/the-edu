package joeuncamp.dabombackend.domain.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.dto.EnrollDto;
import joeuncamp.dabombackend.domain.course.dto.MyCourseDto;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.service.MyCourseService;
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

    @Operation(summary = "강좌에 수강신청합니다.", description = "이미 수강신청한 강좌인 경우 예외가 발생합니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/{courseId}/enroll")
    public ResponseEntity<Void> enroll(@PathVariable Long courseId, @AuthenticationPrincipal Member member) {
        EnrollDto.Request requestDto = new EnrollDto.Request(member.getId(), courseId);
        enrollService.enroll(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "강좌 수강 등록 여부를 조회합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/courses/{courseId}/enroll/check")
    public ResponseEntity<Boolean> doesEnrolled(@PathVariable Long courseId, @AuthenticationPrincipal Member member) {
        EnrollDto.Request requestDto = new EnrollDto.Request(member.getId(), courseId);
        Boolean response = enrollService.doesEnrolled(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "찜을 하거나, 해제합니다.", description = "이미 찜이 되어있는 경우, 해제합니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/{courseId}/wish")
    public ResponseEntity<Void> toggleWish(@PathVariable Long courseId, @AuthenticationPrincipal Member member) {
        WishDto.Request requestDto = new WishDto.Request(member.getId(), courseId);
        wishService.toggleWish(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "찜한 강좌인지 확인합니다.", description = "찜이 되어있는 경우, true를 반환합니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/{courseId}/wish/check")
    public ResponseEntity<Boolean> checkWish(@PathVariable Long courseId, @AuthenticationPrincipal Member member) {
        WishDto.Request requestDto = new WishDto.Request(member.getId(), courseId);
        Boolean response = wishService.checkWish(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "시청 완료한 강좌 목록을 확인합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/courses/completed")
    public ResponseEntity<List<MyCourseDto.Response>> getCompletedCourses(@AuthenticationPrincipal Member member) {
        MyCourseDto.Request requestDto = new MyCourseDto.Request(member.getId());
        List<MyCourseDto.Response> responseDto = myCourseService.getCompletedCourses(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "시청 중인 강좌 목록을 확인합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/courses/ongoing")
    public ResponseEntity<List<MyCourseDto.Response>> getOngoingCourses(@AuthenticationPrincipal Member member) {
        MyCourseDto.Request requestDto = new MyCourseDto.Request(member.getId());
        List<MyCourseDto.Response> responseDto = myCourseService.getOngoingCourses(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
