package joeuncamp.dabombackend.util.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    /**
     * 이메일을 전송합니다.
     *
     * @param email
     */
    public void sendMail(Email email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getMessage(), true);
            javaMailSender.send(mimeMessage);
            log.info("이메일 전송 성공");
        } catch (MessagingException e) {
            log.info("이메일 전송 실패");
            throw new CInternalServerException();
        }
    }

    public static Email passwordResetEmail(String password, String destination) {
        String subject = "온라인 강의 플랫폼 (다봄) 비밀번호 재발급 안내 메일입니다.";
        String message = "<h2> 다봄 비밀번호 재발급 안내 </h2><br>"
                + "<p> 이 이메일은 온라인 강의 플랫폼 다봄 계정의 비밀번호 재발급을 위해 발송된 메일입니다. <br>" +
                "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요.</p> " +
                "<br><h4>임시 비밀번호: " + password + "</h4><br>" +
                "<br>언제 어디서나 저희 서비스를 유용하게 쓰시고 많은 피드백 부탁드립니다. <br>" +
                "저희 다봄 서비스 개발팀은 안정된 서비스와 훌륭한 기능들을 제공하기 위해 불철주야 노력하고 있습니다. <br>" +
                "불편한 점이나 기능 건의는 언제든지 알려주세요! <br><br>";

        return Email.builder()
                .to(destination)
                .subject(subject)
                .message(message)
                .build();
    }

    public static Email ActivatedEmail(Course course, String destination) {
        String subject = "온라인 강의 플랫폼 (다봄) 강좌 개설이 완료되었음을 알려드립니다.";
        String message = "<h2> " + course.getTitle() + " 강좌가 개설되었습니다. </h2><br>"
                + "<p> 다봄에서 당신의 좋은 영향력을 마음껏 펼쳐주세요 ! <br>";
        return Email.builder()
                .to(destination)
                .subject(subject)
                .message(message)
                .build();
    }
}
