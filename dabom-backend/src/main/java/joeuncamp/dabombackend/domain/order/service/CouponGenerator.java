package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.order.dto.CouponDto;
import joeuncamp.dabombackend.domain.order.entity.Coupon;
import joeuncamp.dabombackend.domain.order.entity.Issue;
import joeuncamp.dabombackend.domain.order.repository.CouponJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.IssueJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponGenerator {

    private final CouponJpaRepository couponJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final IssueJpaRepository issueJpaRepository;

    /**
     * 쿠폰을 생성합니다.
     *
     * @param requestDto 쿠폰 발급 정보
     * @return 쿠폰
     */
    public void generate(CouponDto.GenerateRequest requestDto) {
        Coupon coupon = Coupon.builder()
                .code(UUID.randomUUID().toString())
                .discountPolicy(requestDto.getDiscountPolicy())
                .discount(requestDto.getDiscount())
                .endDate(requestDto.getEndDate())
                .minimumAmount(requestDto.getMinimumAmount())
                .build();
        couponJpaRepository.save(coupon);
    }

    /**
     * 회원에게 쿠폰을 발급합니다.
     *
     * @param requestDto 발급할 쿠폰, 발급할 회원
     */
    public void issue(CouponDto.IssueRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberExistException::new);
        Coupon coupon = couponJpaRepository.findById(requestDto.getCouponId()).orElseThrow(CResourceNotFoundException::new);
        Issue issue = Issue.builder()
                .member(member)
                .coupon(coupon)
                .build();
        issueJpaRepository.save(issue);
    }
}
