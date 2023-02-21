package joeuncamp.dabombackend.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.admin.dto.MemberAdminDto;
import joeuncamp.dabombackend.domain.admin.service.MemberAdminService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "[0-1.Admin-Member]", description = "회원 관리 API입니다.")
@RequestMapping("/admin-api")
@RequiredArgsConstructor
public class MemberAdminController {

    private final MemberAdminService memberAdminService;

    @Operation(summary = "전체 회원 조회", description = "서비스 내의 모든 회원을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/members")
    public ResponseEntity<List<MemberAdminDto.ShortResponse>> getAllMember() {
        List<MemberAdminDto.ShortResponse> responseDto = memberAdminService.getAllMember();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "토스 본인인증 토큰 발급", description = "본인인증을 위한 토큰을 발급합니다. 유효기간은 1년입니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cert/token")
    public ResponseEntity<String> issueTossToken() {
        String responseDto = memberAdminService.issueTossToken();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "정지된 회원 목록 조회", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/members/locked")
    public ResponseEntity<List<MemberAdminDto.ShortResponse>> getLockedMembers() {
        List<MemberAdminDto.ShortResponse> responseDto = memberAdminService.getLockedMembers();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "회원 정지", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/members/{memberId}/lock")
    public ResponseEntity<Void> lockMember(@PathVariable Long memberId) {
        memberAdminService.lockMember(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "회원 정지 해제", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/members/{memberId}/unlock")
    public ResponseEntity<Void> unlockMember(@PathVariable Long memberId) {
        memberAdminService.unlockMember(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
