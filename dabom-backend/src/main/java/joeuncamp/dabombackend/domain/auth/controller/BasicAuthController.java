package joeuncamp.dabombackend.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import joeuncamp.dabombackend.domain.auth.dto.ReissueRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.UnlinkRequestDto;
import joeuncamp.dabombackend.domain.auth.service.BasicAuthService;
import joeuncamp.dabombackend.domain.auth.dto.LoginRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.SignupRequestDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[Authentication]", description = "인증과 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BasicAuthController {

    private final BasicAuthService basicAuthService;

    @Operation(summary = "이메일로 회원가입합니다.", description = "")
    @PostMapping("/auth/basic/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        basicAuthService.signup(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "이메일로 로그인합니다.", description = "")
    @PostMapping("/auth/basic/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenForm> login(@RequestBody @Valid LoginRequestDto requestDto) {
        TokenForm responseDto = basicAuthService.login(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @Operation(summary = "로그아웃합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/basic/logout")
    public ResponseEntity<?> logout(@RequestBody @Valid UnlinkRequestDto requestDto, @RequestHeader(Header.JWT_HEADER) String accessToken) {
        basicAuthService.logout(requestDto, accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "회원 탈퇴합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/basic/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody @Valid UnlinkRequestDto requestDto, @RequestHeader(Header.JWT_HEADER) String accessToken) {
        basicAuthService.withdraw(requestDto, accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "어세스토큰과 리프레시토큰을 재발급합니다.", description = "만료된 어세스토큰으로만 리이슈를 호출할 수 있습니다.<br>" +
            "리프레시토큰도 만료된 경우, 리이슈에 실패합니다.")
    @PostMapping("/auth/reissue")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenForm> reissue(@RequestBody @Valid ReissueRequestDto requestDto) {
        TokenForm responseDto = basicAuthService.reissue(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
