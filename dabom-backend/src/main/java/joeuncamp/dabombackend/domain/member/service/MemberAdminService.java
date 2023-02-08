package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.MemberAdminDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAdminService {
    private final MemberJpaRepository memberJpaRepository;
    public List<MemberAdminDto.ShortResponse> getAllMember() {
        List<Member> members = memberJpaRepository.findAll();
        return members.stream()
                .map(MemberAdminDto.ShortResponse::new)
                .toList();
    }
}
