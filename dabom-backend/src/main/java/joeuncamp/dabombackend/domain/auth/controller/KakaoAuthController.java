package joeuncamp.dabombackend.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import joeuncamp.dabombackend.domain.auth.dto.SocialUnlinkRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.KakaoAuthDto;
import joeuncamp.dabombackend.domain.auth.service.KakaoAuthService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[1-2.Kakao Authentication]", description = "카카오 회원 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KakaoAuthController {
    private final KakaoAuthService kakaoAuthService;

    @Operation(summary = "카카오로 로그인합니다.", description = "계정이 없을경우, 계정 생성 후 로그인합니다.")
    @PreAuthorize("permitAll()")
    @PostMapping("/auth/kakao/login")
    public ResponseEntity<KakaoAuthDto.LoginResponse> login(@RequestBody @Valid KakaoAuthDto.LoginRequest requestDto) {
        KakaoAuthDto.LoginResponse responseDto = kakaoAuthService.login(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "로그아웃합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @Parameter(name = Header.REFRESH_TOKEN, description = "리프레시토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.REFRESH)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/kakao/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid KakaoAuthDto.UnlinkRequest requestDto, @RequestHeader(Header.ACCESS_TOKEN) String accessToken, @RequestHeader(Header.REFRESH_TOKEN) String refreshToken) {
        requestDto.setAccessToken(accessToken);
        requestDto.setRefreshToken(refreshToken);
        kakaoAuthService.logout(requestDto, accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "회원 탈퇴합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @Parameter(name = Header.REFRESH_TOKEN, description = "리프레시토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.REFRESH)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/kakao/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody KakaoAuthDto.UnlinkRequest requestDto, @RequestHeader(Header.ACCESS_TOKEN) String accessToken, @RequestHeader(Header.REFRESH_TOKEN) String refreshToken) {
        requestDto.setAccessToken(accessToken);
        requestDto.setRefreshToken(refreshToken);
        kakaoAuthService.withdraw(requestDto, accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
