package joeuncamp.dabombackend.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.auth.dto.AppleAuthDto;
import joeuncamp.dabombackend.domain.auth.dto.KakaoLoginRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.SocialUnlinkRequestDto;
import joeuncamp.dabombackend.domain.auth.service.AppleAuthService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[1-3.Apple Authentication]", description = "애플 회원 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AppleAuthController {

    private final AppleAuthService appleAuthService;

    @Operation(summary = "애플로 가입합니다.", description = "")
    @PreAuthorize("permitAll()")
    @PostMapping("/auth/apple/signup")
    public ResponseEntity<Void> signup(@RequestBody AppleAuthDto.SignupRequest requestDto) {
        appleAuthService.signup(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "애플로 로그인합니다.", description = "")
    @PreAuthorize("permitAll()")
    @PostMapping("/auth/apple/login")
    public ResponseEntity<TokenForm> login(@RequestBody AppleAuthDto.LoginRequest requestDto) {
        TokenForm responseDto = appleAuthService.login(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "애플로 로그아웃합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @Parameter(name = Header.JWT_HEADER, description = "리프레시토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.REFRESH)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/apple/logout")
    public ResponseEntity<TokenForm> logout(@RequestBody AppleAuthDto.LogoutRequest requestDto) {
        appleAuthService.logout(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
