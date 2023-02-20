package joeuncamp.dabombackend.domain.auth.service;

import joeuncamp.dabombackend.domain.auth.repository.EmailAuthKeyRedisRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.error.exception.CCertificationFailedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import joeuncamp.dabombackend.util.RandomStringGenerator;
import joeuncamp.dabombackend.util.email.Email;
import joeuncamp.dabombackend.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailCertificationService {
    private final EmailService emailService;
    private final EmailAuthKeyRedisRepository emailAuthKeyRedisRepository;
    private final MemberJpaRepository memberJpaRepository;

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

    public void certifyEmail(String email, String authKey) {
        Optional<String> authKeyOptional = emailAuthKeyRedisRepository.findByEmail(email);
        if (authKeyOptional.isEmpty() || !authKeyOptional.get().equals(authKey)){
            throw new CCertificationFailedException();
        }
        emailAuthKeyRedisRepository.deleteByEmail(email);
        Member member = memberJpaRepository.findByAccountAndLoginType(email, LoginType.BASIC).orElseThrow(CResourceNotFoundException::new);
        member.setEmailCertified();
        memberJpaRepository.save(member);
    }
}
