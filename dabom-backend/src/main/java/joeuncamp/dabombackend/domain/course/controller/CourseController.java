package joeuncamp.dabombackend.domain.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import joeuncamp.dabombackend.domain.course.dto.*;
import joeuncamp.dabombackend.domain.course.service.CourseService;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.course.service.RankingService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.wish.dto.WishDto;
import joeuncamp.dabombackend.domain.wish.service.WishService;
import joeuncamp.dabombackend.global.common.IdResponseDto;
import joeuncamp.dabombackend.global.common.PagingDto;
import joeuncamp.dabombackend.global.common.SingleResponseDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "[3.Course]", description = "클래스와 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CourseController {
    private final CourseService courseService;
    private final EnrollService enrollService;
    private final WishService wishService;

    private final RankingService rankingService;

    @Operation(summary = "강좌를 개설합니다.", description = "강좌 개설은 크리에이터 프로필을 활성화한 회원만 가능합니다. \n 개설된 강좌의 아이디넘버가 반환됩니다.")
    @Parameter(name = Header.JWT_HEADER, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses")
    public ResponseEntity<IdResponseDto> openCourse(@AuthenticationPrincipal Member member, @RequestBody CourseDto.CreationRequest requestDto) {
        requestDto.setMemberId(member.getId());
        IdResponseDto response = courseService.openCourse(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "강좌를 단건 조회합니다.", description = "")
    @PreAuthorize("permitAll()")
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<CourseDto.Response> getCourse(@PathVariable Long courseId) {
        CourseDto.Response responseDto = courseService.getCourse(courseId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "카테고리 내의 전체 강좌를 조회합니다.", description = "정해진 카테고리가 아닐 경우 예외가 반환됩니다.<br>카테고리 목록 : 카테고리 API 참조")
    @PreAuthorize("permitAll()")
    @GetMapping("/courses/category/{category}")
    public ResponseEntity<PagingDto<CourseDto.ShortResponse>> getCourseByCategory(@PathVariable @Schema(example = "백엔드") String category, @ParameterObject @PageableDefault(sort = "title") Pageable pageable) {
        PagingDto<CourseDto.ShortResponse> responseDto = courseService.getCoursesByCategory(category, pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "강좌를 검색합니다.", description = "제목이나 강사명 중에 검색어가 포함되어 있는 강좌를 모두 조회합니다.")
    @PreAuthorize("permitAll()")
    @GetMapping("/courses/keyword/{keyword}")
    public ResponseEntity<PagingDto<CourseDto.ShortResponse>> searchCourses(@PathVariable @Schema(example = "검색어") String keyword, @ParameterObject @PageableDefault(sort = "title") Pageable pageable) {
        PagingDto<CourseDto.ShortResponse> responseDto = courseService.searchCourses(keyword, pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

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
    public ResponseEntity<SingleResponseDto<Boolean>> doesEnrolled(@PathVariable Long courseId, @AuthenticationPrincipal Member member) {
        EnrollDto.Request requestDto = new EnrollDto.Request(member.getId(), courseId);
        SingleResponseDto<Boolean> responseDto = enrollService.doesEnrolled(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
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
    public ResponseEntity<SingleResponseDto<Boolean>> checkWish(@PathVariable Long courseId, @AuthenticationPrincipal Member member) {
        WishDto.Request requestDto = new WishDto.Request(member.getId(), courseId);
        SingleResponseDto<Boolean> responseDto = wishService.checkWish(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @Operation(summary = "강좌 랭킹을 조회합니다.", description = "일주일 간격으로 갱신됩니다.")
    @PreAuthorize("permitAll()")
    @GetMapping("/courses/category/{category}/ranking")
    public ResponseEntity<List<CourseDto.ShortResponse>> getRanking(@PathVariable @Schema(example = "백엔드") String category) {
        List<CourseDto.ShortResponse> responseDto = rankingService.findTop4FromCategory(category);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
