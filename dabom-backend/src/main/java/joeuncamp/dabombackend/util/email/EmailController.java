package joeuncamp.dabombackend.util.email;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "메일 전송", description = "메일을 전송합니다.")
    @PreAuthorize("permitAll()")
    @PostMapping("/email")
    public ResponseEntity<?> sendEmail() {
        String tempPassword = "test1234!";
        String subject = "온라인 강의 플랫폼 (다봄) 비밀번호 재발급 안내 메일입니다.";
        String message = "<h2> 다봄 비밀번호 재발급 안내 </h2><br>"
                + "<p> 이 이메일은 온라인 강의 플랫폼 다봄 계정의 비밀번호 재발급을 위해 발송된 메일입니다. <br>" +
                "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요.</p> " +
                "<br><h4>임시 비밀번호: " + tempPassword + "</h4><br>" +
                "언제 어디서나 저희 서비스를 유용하게 쓰시고 많은 피드백 부탁드립니다. <br>" +
                "저희 다봄 서비스 개발팀은 안정된 서비스와 훌륭한 기능들을 제공하기 위해 불철주야 노력하고 있습니다. <br>" +
                "불편한 점이나 기능 건의는 언제든지 알려주세요.";

        Email email = Email.builder()
                .to("hansol8701@naver.com")
                .subject(subject)
                .message(message)
                .build();


        emailService.sendMail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
