package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.order.entity.Issue;
import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.domain.order.repository.IssueJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueJpaRepository issueJpaRepository;

    /**
     * 상품에 쿠폰을 적용합니다.
     * 이미 사용한 쿠폰이거나, 쿠폰 적용이 불가능한 상품인 경우 예외가 발생합니다.
     * 해당 쿠폰을 '사용'처리하고, 할인된 금액을 반환합니다.
     *
     * @param issue 쿠폰 발급 정보
     * @param item  쿠폰을 적용할 상품
     * @return 쿠폰 적용된 금액
     */
    public long useCoupon(Issue issue, Item item) {
        if (isAvailable(issue, item)) {
            throw new CBadRequestException("사용 불가능한 쿠폰입니다.");
        }
        issue.expire();
        issueJpaRepository.save(issue);
        return issue.getCoupon().calculateDiscountedPrice(item.getPrice().getDiscountedPrice());
    }

    /**
     * 사용할 수 있는 쿠폰인지 확인합니다.
     * 사용했거나, 해당 아이템에 적용할 수 없는 쿠폰이면 false가 반환됩니다.
     *
     * @param issue 쿠폰 발급 정보
     * @param item  상품
     * @return true/false
     */
    public boolean isAvailable(Issue issue, Item item) {
        return !issue.isUsed() && issue.getCoupon().isValid(item);
    }
}
