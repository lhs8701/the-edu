package joeuncamp.dabombackend.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import joeuncamp.dabombackend.domain.member.dto.PasswordDto;
import joeuncamp.dabombackend.domain.member.dto.ProfileDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.service.MemberService;
import joeuncamp.dabombackend.domain.member.service.AccountManager;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final AccountManager accountManager;

    @Operation(summary = "자신의 프로필 정보를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/members/me/profile")
    public ResponseEntity<ProfileDto.Response> getMyProfile(@AuthenticationPrincipal Member member) {
        ProfileDto.Response responseDto = memberService.getMyProfile(member.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "자신의 회원 정보를 수정합니다.", description = "수정하지 않을 항목은 비워두면 됩니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/members/me/profile")
    public ResponseEntity<Void> updateMyProfile(@RequestBody @Valid ProfileDto.UpdateRequest updateParam, @AuthenticationPrincipal Member member) {
        updateParam.setMemberId(member.getId());
        memberService.updateMyProfile(updateParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "임시 비밀번호 발급", description = "회원의 이메일로 임시 비밀번호를 전송합니다.")
    @PreAuthorize("permitAll()")
    @PostMapping("/members/me/password/reset")
    public ResponseEntity<PasswordDto.Response> resetPassword(@RequestBody @Valid PasswordDto.ResetRequest requestDto) {
        PasswordDto.Response response = accountManager.resetPassword(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "비밀번호 변경", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/members/me/password/change")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid PasswordDto.ChangeRequest requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        accountManager.changePassword(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "포인트 조회", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/members/me/point")
    public ResponseEntity<Long> getPoint(@AuthenticationPrincipal Member member) {
        return new ResponseEntity<>(member.getPayPoint(), HttpStatus.OK);
    }
}

