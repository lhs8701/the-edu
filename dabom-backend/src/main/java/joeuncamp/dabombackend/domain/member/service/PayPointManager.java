package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import joeuncamp.dabombackend.global.error.exception.CMemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayPointManager {
    private final MemberJpaRepository memberJpaRepository;

    /**
     * 회원의 포인트를 사용합니다.
     *
     * @param member 회원
     * @param point  사용할 포인트
     * @return 사용한 포인트
     */
    public long usePoint(Member member, long point) {
        if (member.getPayPoint() < point) {
            throw new CBadRequestException("포인트가 부족합니다.");
        }
        member.updatePoint(-point);
        memberJpaRepository.save(member);
        return point;
    }

    /**
     * 포인트를 적립합니다.
     *
     * @param member 회원
     * @param point  적립할 포인트
     */
    public void raisePoint(Member member, long point) {
        member.updatePoint(point);
        memberJpaRepository.save(member);
    }
}
