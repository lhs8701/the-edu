package joeuncamp.dabombackend.domain.unit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.unit.dto.UnitDto;
import joeuncamp.dabombackend.domain.unit.service.UnitService;
import joeuncamp.dabombackend.global.common.SingleResponseDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "[4.Unit]", description = "강의 관련 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @Operation(summary = "강의를 업로드합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/{courseId}/units")
    public ResponseEntity<SingleResponseDto<Long>> uploadUnit(@PathVariable Long courseId, UnitDto.UploadRequest requestDto, @AuthenticationPrincipal Member member) {
        UnitDto unitDto = UnitDto.builder()
                .memberId(member.getId())
                .courseId(courseId)
                .uploadRequest(requestDto)
                .build();
        SingleResponseDto<Long> responseDto = unitService.uploadUnit(unitDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "강의를 재생합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/courses/units/{unitId}")
    public ResponseEntity<UnitDto.Response> playUnit(@PathVariable Long unitId, @AuthenticationPrincipal Member member) {
        UnitDto unitDto = UnitDto.builder()
                .memberId(member.getId())
                .unitId(unitId)
                .build();
        UnitDto.Response responseDto = unitService.playUnit(unitDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
