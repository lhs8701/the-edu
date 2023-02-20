package joeuncamp.dabombackend.domain.auth.service;

import joeuncamp.dabombackend.domain.auth.repository.EmailAuthKeyRedisRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.util.RandomStringGenerator;
import joeuncamp.dabombackend.util.email.Email;
import joeuncamp.dabombackend.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailCertificationService {
    private final EmailService emailService;
    private final EmailAuthKeyRedisRepository emailAuthKeyRedisRepository;

    /**
     * 이메일 - 인증키 쌍을 레디스에 저장한 후, 인증 요청 메일을 발송합니다.
     *
     * @param email 이메일
     */
    public void sendCertificationLink(String email) {
        String authKey = RandomStringGenerator.generateEmailAuthKey();
        emailAuthKeyRedisRepository.saveAuthKey(email, authKey);
        emailService.sendMail(Email.emailCertificationEmail(email, authKey));
    }
}
