package joeuncamp.dabombackend.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.dto.ProfileResponseDto;
import joeuncamp.dabombackend.domain.member.dto.ProfileUpdateParam;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.service.MemberService;
import joeuncamp.dabombackend.global.common.IdResponseDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "[2-1.Member]", description = "회원과 관련된 API입니다.")
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 정보를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{memberId}/profile")
    public ResponseEntity<ProfileResponseDto> getMyProfile(@PathVariable Long memberId) {
        return new ResponseEntity<>(memberService.getMyProfile(memberId), HttpStatus.OK);
    }

    @Operation(summary = "자신의 회원 정보를 수정합니다.", description = "수정하지 않을 항목은 비워두면 됩니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{memberId}/profile")
    public ResponseEntity<IdResponseDto> updateMyProfile(@AuthenticationPrincipal Member member, @RequestBody ProfileUpdateParam updateParam, @PathVariable Long memberId) {
        IdResponseDto responseDto = memberService.updateMyProfile(updateParam, member.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

