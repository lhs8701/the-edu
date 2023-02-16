package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.order.dto.CouponDto;
import joeuncamp.dabombackend.domain.order.entity.Coupon;
import joeuncamp.dabombackend.domain.order.entity.Issue;
import joeuncamp.dabombackend.domain.order.repository.CouponJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.IssueJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import joeuncamp.dabombackend.util.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Coupon coupon = requestDto.toEntity();
        couponJpaRepository.save(coupon);
    }

    /**
     * 관리자가 회원에게 쿠폰을 발급합니다.
     *
     * @param requestDto 발급할 쿠폰, 발급할 회원
     */
    public void issue(CouponDto.IssueRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberExistException::new);
        Coupon coupon = couponJpaRepository.findById(requestDto.getCouponId()).orElseThrow(CResourceNotFoundException::new);
        if (issueJpaRepository.findByMemberAndCoupon(member, coupon).isPresent()) {
            throw new CBadRequestException("이미 발급한 쿠폰입니다.");
        }
        Issue issue = Issue.builder()
                .member(member)
                .coupon(coupon)
                .build();
        issueJpaRepository.save(issue);
    }

    /**
     * 쿠폰의 랜덤 코드를 생성합니다.
     * 회원은 랜덤 코드를 사용해서 쿠폰을 발급할 수 있습니다.
     *
     * @param couponId 코드를 생성할 쿠폰
     */
    public void generateCouponCode(Long couponId) {
        Coupon coupon = couponJpaRepository.findById(couponId).orElseThrow(CResourceNotFoundException::new);
        List<String> codeList = coupon.getCode();
        String code = getValidCode(codeList);
        codeList.add(code);
        couponJpaRepository.save(coupon);
    }

    private String getValidCode(List<String> codeList) {
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String code;
        while (codeList.contains(code = randomStringGenerator.generateCouponCode())) ;
        return code;
    }
}
