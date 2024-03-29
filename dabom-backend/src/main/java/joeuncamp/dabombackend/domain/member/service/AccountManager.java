package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.PasswordDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import joeuncamp.dabombackend.global.error.exception.CMemberNotFoundException;
import joeuncamp.dabombackend.global.error.exception.CWrongPasswordException;
import joeuncamp.dabombackend.util.RandomStringGenerator;
import joeuncamp.dabombackend.util.email.Email;
import joeuncamp.dabombackend.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountManager {

    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final RandomStringGenerator randomStringGenerator;
    private final EmailService emailService;

    /**
     * 임시 비밀번호를 설정합니다.
     *
     * @param requestDto 회원, 현재 비밀번호
     */
    public PasswordDto.Response resetPassword(PasswordDto.ResetRequest requestDto) {
        Member member = memberJpaRepository.findByAccountAndLoginTypeAndLockedIsFalse(requestDto.getAccount(), LoginType.BASIC).orElseThrow(CMemberNotFoundException::new);
        String newPassword = randomStringGenerator.generatePassword();
        member.changePassword(passwordEncoder.encode(newPassword));
        memberJpaRepository.save(member);
        emailService.sendMail(Email.passwordReissueEmail(member.getEmail(), newPassword));
        return new PasswordDto.Response(member.getEmail());
    }

    /**
     * 비밀번호를 변경합니다.
     * 기존 비밀번호를 틀리게 입력할 경우, 예외가 반환됩니다.
     *
     * @param requestDto 현재 비밀번호, 새 비밀번호, 회원
     */
    public void changePassword(PasswordDto.ChangeRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberNotFoundException::new);
        if (member.getLoginType() != LoginType.BASIC){
            throw new CIllegalArgumentException("일반 회원가입 유저만 비밀번호를 변경할 수 있습니다.");
        }
        if (!doesMatched(requestDto, member)) {
            throw new CWrongPasswordException();
        }
        member.changePassword(passwordEncoder.encode(requestDto.getNewPassword()));
        memberJpaRepository.save(member);
    }

    private boolean doesMatched(PasswordDto.ChangeRequest requestDto, Member member) {
        return passwordEncoder.matches(requestDto.getCurrentPassword(), member.getPassword());
    }
}
