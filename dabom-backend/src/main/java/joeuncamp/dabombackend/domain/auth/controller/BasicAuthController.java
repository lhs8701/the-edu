package joeuncamp.dabombackend.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import joeuncamp.dabombackend.domain.auth.dto.*;
import joeuncamp.dabombackend.domain.auth.service.BasicAuthService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[1-1.Basic Authentication]", description = "인증과 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BasicAuthController {

    private final BasicAuthService basicAuthService;

    @Operation(summary = "이메일로 회원가입합니다.", description = "")
    @PostMapping("/auth/basic/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> signup(@RequestBody @Valid BasicAuthDto.SignupRequest requestDto) {
        basicAuthService.signup(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "이메일로 로그인합니다.", description = "")
    @PostMapping("/auth/basic/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BasicAuthDto.LoginResponse> login(@RequestBody @Valid BasicAuthDto.LoginRequest requestDto) {
        BasicAuthDto.LoginResponse responseDto = basicAuthService.login(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @Operation(summary = "로그아웃합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @Parameter(name = Header.REFRESH_TOKEN, description = "리프레시토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.REFRESH)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/basic/logout")
    public ResponseEntity<Void> logout(@RequestHeader(Header.ACCESS_TOKEN) String accessToken, @RequestHeader(Header.REFRESH_TOKEN) String refreshToken) {
        BasicAuthDto.UnlinkRequestDto requestDto = new BasicAuthDto.UnlinkRequestDto(accessToken, refreshToken);
        basicAuthService.logout(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "회원 탈퇴합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @Parameter(name = Header.REFRESH_TOKEN, description = "리프레시토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.REFRESH)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/basic/withdraw")
    public ResponseEntity<Void> withdraw(@RequestHeader(Header.ACCESS_TOKEN) String accessToken, @RequestHeader(Header.REFRESH_TOKEN) String refreshToken) {
        BasicAuthDto.UnlinkRequestDto requestDto = new BasicAuthDto.UnlinkRequestDto(accessToken, refreshToken);
        basicAuthService.withdraw(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "어세스토큰과 리프레시토큰을 재발급합니다.", description = "만료된 어세스토큰으로만 리이슈를 호출할 수 있습니다.<br>" +
            "리프레시토큰도 만료된 경우, 리이슈에 실패합니다.")
    @PostMapping("/auth/reissue")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenForm> reissue(@RequestBody @Valid BasicAuthDto.ReissueRequest requestDto) {
        TokenForm responseDto = basicAuthService.reissue(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
