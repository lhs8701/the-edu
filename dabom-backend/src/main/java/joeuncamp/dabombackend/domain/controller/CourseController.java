package joeuncamp.dabombackend.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.dto.CourseCreationRequestDto;
import joeuncamp.dabombackend.domain.course.dto.CourseResponseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.service.CourseService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[Course]", description = "클래스와 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @Operation(summary = "강좌를 개설합니다.", description = "강좌 개설은 크리에이터 프로필을 활성화한 회원만 가능합니다. \n 개설된 강좌의 아이디넘버가 반환됩니다.")
    @Parameter(name = Header.JWT_HEADER, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<Long> openCourse(@AuthenticationPrincipal Member member, @RequestBody CourseCreationRequestDto dto){
        return new ResponseEntity<>(1L, HttpStatus.CREATED);
    }


    @Operation(summary="", description="")
    @Parameter(name = Header.JWT_HEADER, description="어세스토큰", required=true, in=ParameterIn.HEADER, example=ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDto> getCourse(@PathVariable Long courseId){
        CourseResponseDto responseDto = courseService.getCourse(courseId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
