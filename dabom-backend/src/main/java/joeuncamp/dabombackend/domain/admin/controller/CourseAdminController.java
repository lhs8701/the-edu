package joeuncamp.dabombackend.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.admin.service.CourseAdminService;
import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "[0-3.Admin-Course]", description = "강좌 관리 API입니다.")
@RequestMapping("/admin-api")
@RequiredArgsConstructor
public class CourseAdminController {
    private final CourseAdminService courseAdminService;

    @Operation(summary = "대기 상태의 강좌 목록 조회", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/courses/inactive")
    public ResponseEntity<List<CourseDto.ShortResponse>> getInactiveCourses() {
        List<CourseDto.ShortResponse> responseDto = courseAdminService.getInactiveCourses();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "대기 상태의 강좌 활성화", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/courses/{courseId}/activate")
    public ResponseEntity<List<CourseDto.ShortResponse>> activateCourse(@PathVariable Long courseId) {
        courseAdminService.activateCourse(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
