package joeuncamp.dabombackend.domain.unit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.unit.dto.UnitDto;
import joeuncamp.dabombackend.domain.unit.service.UnitService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "[4-1.Unit]", description = "강의 관련 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @Operation(summary = "강의를 업로드합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/{courseId}/units")
    public ResponseEntity<Long> uploadUnit(@PathVariable Long courseId, UnitDto.UploadRequest requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        requestDto.setCourseId(courseId);
        Long response = unitService.uploadUnit(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "강의 재생을 위한 세부정보를 조회합니다.", description = "영상의 세부 정보와 함께 URL이 반환됩니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/courses/units/{unitId}")
    public ResponseEntity<UnitDto.Response> playUnit(@PathVariable Long unitId, @AuthenticationPrincipal Member member) {
        UnitDto.PlayRequest requestDto = new UnitDto.PlayRequest(member.getId(), unitId);
        UnitDto.Response responseDto = unitService.playUnit(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
