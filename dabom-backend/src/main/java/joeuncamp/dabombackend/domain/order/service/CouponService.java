package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.entity.Coupon;
import joeuncamp.dabombackend.domain.order.entity.Issue;
import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.domain.order.repository.CouponJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.IssueJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final IssueJpaRepository issueJpaRepository;

    public long useCoupon(Member member, Coupon coupon, Item item) {
        Issue issue = issueJpaRepository.findByMemberAndCoupon(member, coupon).orElseThrow(CResourceNotFoundException::new);
        if (issue.isUsed()) {
            throw new CBadRequestException("이미 사용한 쿠폰입니다.");
        }
        if (!coupon.isValid(item)){
            throw new CBadRequestException("사용 불가능한 쿠폰입니다.");
        }
        issue.expire();
        issueJpaRepository.save(issue);
        return coupon.calculateDiscountedPrice(item.getPrice().getDiscountedPrice());
    }
}
