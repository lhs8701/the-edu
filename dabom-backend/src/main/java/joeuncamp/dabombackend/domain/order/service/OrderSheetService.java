package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.order.dto.CouponDto;
import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.domain.order.dto.OrderSheetDto;
import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.domain.order.repository.IssueJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.ItemJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CMemberNotFoundException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class OrderSheetService {

    private final MemberJpaRepository memberJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final IssueService issueService;
    private final IssueJpaRepository issueJpaRepository;

    /**
     * 구매를 위한 주문서 정보를 조회합니다.
     *
     * @param requestDto 구매할 상품, 회원
     * @return 주문서 정보
     */
    public OrderSheetDto.Response getOrderSheet(OrderSheetDto.Request requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberNotFoundException::new);
        Item item = itemJpaRepository.findById(requestDto.getItemId()).orElseThrow(CResourceNotFoundException::new);
        List<CouponDto.Response> issueList = issueJpaRepository.findByMemberAndUsedIsFalse(member).stream()
                .filter(issue -> issueService.isAvailable(issue, item))
                .map(issue -> new CouponDto.Response(issue.getCoupon()))
                .toList();
        return OrderSheetDto.Response.builder()
                .item(item)
                .member(member)
                .couponList(issueList)
                .build();
    }
}
