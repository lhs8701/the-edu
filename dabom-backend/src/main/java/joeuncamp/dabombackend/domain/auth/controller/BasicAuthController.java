package joeuncamp.dabombackend.domain.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import joeuncamp.dabombackend.domain.auth.service.BasicAuthService;
import joeuncamp.dabombackend.domain.auth.dto.LoginRequestDto;
import joeuncamp.dabombackend.domain.auth.dto.SignupRequestDto;
import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[Authentication]", description = "인증과 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/basic")
public class BasicAuthController {

    private final BasicAuthService basicAuthService;

    @PostMapping("signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequestDto signUpRequestDto) {
        basicAuthService.signup(signUpRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenForm> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        TokenForm tokenForm = basicAuthService.login(loginRequestDto);
        return new ResponseEntity<>(tokenForm, HttpStatus.OK);
    }

    @PostMapping("/test")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> test(){
        basicAuthService.AuthenticationTest();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
