package joeuncamp.dabombackend.domain.admin.service;

import joeuncamp.dabombackend.domain.admin.dto.MemberAdminDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.util.tossapi.TossService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAdminService {
    private final MemberJpaRepository memberJpaRepository;
    private final TossService tossService;
    public List<MemberAdminDto.ShortResponse> getAllMember() {
        List<Member> members = memberJpaRepository.findAll();
        return members.stream()
                .map(MemberAdminDto.ShortResponse::new)
                .toList();
    }

    /**
     * 토스 본인인증을 위한 토큰을 발급합니다.
     * 만료기간은 1년입니다.
     *
     * @return 토스 토큰
     */
    public String issueTossToken() {
        return tossService.issueToken();
    }
}
