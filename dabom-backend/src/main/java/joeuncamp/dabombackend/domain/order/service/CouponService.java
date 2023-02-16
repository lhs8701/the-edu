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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponJpaRepository couponJpaRepository;
    private final IssueJpaRepository issueJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final CouponGenerator couponGenerator;

    /**
     * 쿠폰 코드로 쿠폰을 발급받습니다.
     *
     * @param requestDto 회원, 쿠폰 코드
     */
    public void registerCoupon(CouponDto.RegisterRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberExistException::new);
        List<Coupon> couponList = couponJpaRepository.findAll();
        Coupon coupon = couponList.stream()
                .filter(c -> c.getCode().contains(requestDto.getCouponCode()))
                .findAny().orElseThrow(CResourceNotFoundException::new);
        couponGenerator.issue(member, coupon);
    }

    /**
     * 사용한 쿠폰 목록을 조회합니다.
     *
     * @param memberId 회원
     * @return 사용한 쿠폰 목록
     */
    public List<CouponDto.Response> getMyUsedCoupons(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CMemberExistException::new);
        List<Issue> issueList = issueJpaRepository.findByMember(member);
        return issueList.stream()
                .filter(Issue::isUsed)
                .map(i -> new CouponDto.Response(i.getCoupon()))
                .toList();
    }
}
