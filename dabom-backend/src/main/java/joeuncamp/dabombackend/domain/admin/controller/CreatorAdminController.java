package joeuncamp.dabombackend.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.admin.dto.CreatorAdminDto;
import joeuncamp.dabombackend.domain.admin.dto.MemberAdminDto;
import joeuncamp.dabombackend.domain.admin.service.CreatorAdminService;
import joeuncamp.dabombackend.domain.admin.service.MemberAdminService;
import joeuncamp.dabombackend.domain.creator.service.CreatorActivator;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.unit.dto.UnitDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "[0-2.Admin-Creator]", description = "크리에이터 관리 API입니다.")
@RequestMapping("/admin-api")
@RequiredArgsConstructor
public class CreatorAdminController {
    private final CreatorAdminService creatorAdminService;

    @Operation(summary = "크리에이터 활성화", description = "회원의 크리에이터 기능을 활성화합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/creators/{creatorId}/active")
    public ResponseEntity<Void> activateCreator(@PathVariable Long creatorId) {
        creatorAdminService.activateCreator(creatorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "대기 상태의 크리에이터 목록 조회", description = "대기 상태인 크리에이터 목록을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/creators/standby")
    public ResponseEntity<List<CreatorAdminDto.Response>> getStandbyCreators() {
        List<CreatorAdminDto.Response> responseDto = creatorAdminService.getStandbyCreators();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @Operation(summary = "전체 크리에이터 목록 조회", description = "전체 크리에이터 목록을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/creators")
    public ResponseEntity<List<CreatorAdminDto.Response>> getCreators() {
        List<CreatorAdminDto.Response> responseDto = creatorAdminService.getCreators();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "강의 재생을 위한 세부정보를 조회합니다.", description = "영상의 세부 정보와 함께 URL이 반환됩니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/courses/units/{unitId}")
    public ResponseEntity<UnitDto.Response> playUnit(@PathVariable Long unitId) {
        UnitDto.Response responseDto = creatorAdminService.playUnit(unitId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
