package joeuncamp.dabombackend.domain.auth.basic;

import joeuncamp.dabombackend.global.security.jwt.TokenForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/basic")
public class AuthController {

    private final AuthService authService;

    @PostMapping("signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        authService.signup(signUpRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenForm> login(@RequestBody LoginRequestDto loginRequestDto) {
        TokenForm tokenForm = authService.login(loginRequestDto);
        return new ResponseEntity<>(tokenForm, HttpStatus.OK);
    }

    @PostMapping("/test")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> test(){
        authService.AuthenticationTest();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
