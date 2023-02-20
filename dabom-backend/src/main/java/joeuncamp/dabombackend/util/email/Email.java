package joeuncamp.dabombackend.util.email;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.global.constant.URI;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Email {
    @Value("${cert.email}")
    static String EMAIL_CERTIFICATION_URI;
    @Schema(description = "수신자")
    String to;
    @Schema(description = "제목")
    String subject;
    @Schema(description = "내용")
    String message;

    public static Email passwordReissueEmail(String destination, String password) {
        String subject = "온라인 강의 플랫폼 (다봄) 비밀번호 재발급 안내 메일입니다.";
        String message = "<h2> 다봄 비밀번호 재발급 안내 </h2><br>"
                + "<p> 이 이메일은 온라인 강의 플랫폼 다봄 계정의 비밀번호 재발급을 위해 발송된 메일입니다. <br>" +
                "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요.</p> " +
                "<br><div style= border: none; background-color: #ffd555; color: #000000; font-size: 14px; font-weight: bolder; height:30px; " +
                "border-radius:10px; width: 300px; margin-bottom:30px; margin-top:30px; padding-top:2px; padding-bottom:2px;>임시 비밀번호: " + password + "</div><br>" +
                "<br>언제 어디서나 저희 서비스를 유용하게 쓰시고 많은 피드백 부탁드립니다. <br>" +
                "저희 다봄 서비스 개발팀은 안정된 서비스와 훌륭한 기능들을 제공하기 위해 불철주야 노력하고 있습니다. <br>" +
                "불편한 점이나 기능 건의는 언제든지 알려주세요! <br><br>";

        return Email.builder()
                .to(destination)
                .subject(subject)
                .message(message)
                .build();
    }

    public static Email courseAcceptEmail(String destination, String courseTitle) {
        String subject = "온라인 강의 플랫폼 (다봄) 강좌 개설이 완료되었음을 알려드립니다.";
        String message = "<h2> " + courseTitle + " 강좌가 개설되었습니다. </h2><br>"
                + "<p> 다봄에서 당신의 좋은 영향력을 마음껏 펼쳐주세요 ! <br>";
        return Email.builder()
                .to(destination)
                .subject(subject)
                .message(message)
                .build();
    }

    public static Email creatorAcceptEmail(String destination, String memberName) {
        String subject = "온라인 강의 플랫폼 (다봄) 크리에이터 승인이 완료되었음을 알려드립니다.";
        String message = "<h2> " + memberName + " 님, 크리에이터 승인이 완료되었습니다. </h2><br>"
                + "<p> 다봄에서 당신의 좋은 영향력을 마음껏 펼쳐주세요 ! <br>";

        return Email.builder()
                .to(destination)
                .subject(subject)
                .message(message)
                .build();
    }

    public static Email emailCertificationEmail(String destination, String authKey) {
        String subject = "온라인 강의 플랫폼 (다봄) 이메일 인증";
        String message = "<h2> 회원가입을 위해 메일을 인증해 주세요 </h2><br>"
                + "<p> 안녕하세요 온라인 강의 플랫폼, 다봄입니다. <br>"
                + "아래 메일 인증 버튼을 눌러 회원가입을 완료해주세요. <br> <br>"
                + "<a  href='"
                + URI.EMAIL_CERTIFICATION_URI
                + "?email="
                + destination
                + "&auth-key="
                + authKey
                + "' target='_blenk'>" +
                "      <button\n" +
                "        style=\"\n" +
                "          border: none;\n" +
                "          background-color: #ffd555;\n" +
                "          color: #000000;\n" +
                "          font-size: 18px;\n" +
                "          font-weight: bolder;\n" +
                "          height: 40px;\n" +
                "          border-radius: 10px;\n" +
                "          width: 350px;\n" +
                "          margin-bottom: 12px;\n" +
                "          margin-top: 30px;\n" +
                "          margin-bottom: 35px;\n" +
                "          padding-top: 2px;\n" +
                "          padding-bottom: 2px;\n" +
                "          cursor: pointer;\n" +
                "          \n" +
                "        \"\n" +
                "      >\n" +
                "        메일 인증\n" +
                "      </button>" +
                "    </a>";

        return Email.builder()
                .to(destination)
                .subject(subject)
                .message(message)
                .build();
    }
}
