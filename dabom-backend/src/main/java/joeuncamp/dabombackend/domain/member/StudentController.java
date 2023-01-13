package joeuncamp.dabombackend.domain.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.dto.MyCourseShortResponseDto;
import joeuncamp.dabombackend.domain.member.service.StudentService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "[Student]", description = "수강생과 관련된 API입니다.")
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @Operation(summary="회원이 등록한 모든 강좌를 조회합니다.", description="")
    @Parameter(name = Header.JWT_HEADER, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{memberId}/courses")
    public ResponseEntity<List<MyCourseShortResponseDto>> getMyCourses(@PathVariable Long memberId){
        List<MyCourseShortResponseDto> responseDto = studentService.getMyCourses(memberId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
