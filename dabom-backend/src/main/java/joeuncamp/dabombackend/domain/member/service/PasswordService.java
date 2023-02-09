package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.PasswordDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.TempPasswordRedisRepository;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import joeuncamp.dabombackend.util.RandomStringGenerator;
import joeuncamp.dabombackend.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final RandomStringGenerator randomStringGenerator;
    private final EmailService emailService;

    /**
     * 임시 비밀번호를 설정합니다.
     *
     * @param requestDto 회원, 현재 비밀번호
     */
    public void resetPassword(PasswordDto.ResetRequest requestDto) {
        Member member = memberJpaRepository.findByAccountAndLoginType(requestDto.getAccount(), LoginType.BASIC).orElseThrow(CMemberExistException::new);
        String newPassword = randomStringGenerator.generatePassword();
        member.changePassword(passwordEncoder.encode(newPassword));
        memberJpaRepository.save(member);
        emailService.sendMail(EmailService.passwordResetEmail(newPassword, member.getEmail()));
    }
}
