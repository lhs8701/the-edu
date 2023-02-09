package joeuncamp.dabombackend.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.dto.CourseStatusDto;
import joeuncamp.dabombackend.domain.course.service.MyCourseService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "[2-3.Student]", description = "수강생과 관련된 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {
    private final MyCourseService myCourseService;

    @Operation(summary = "회원이 찜한 모든 강좌를 조회합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/students/{memberId}/courses/wish")
    public ResponseEntity<List<CourseDto.ShortResponse>> getWishedCourses(@PathVariable Long memberId) {
        List<CourseDto.ShortResponse> responseDto = myCourseService.getWishedCourses(memberId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
