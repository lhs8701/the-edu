package joeuncamp.dabombackend.util.email;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Email {
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
}
