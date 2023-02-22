package joeuncamp.dabombackend.domain.admin.service;

import joeuncamp.dabombackend.domain.admin.dto.CouponAdminDto;
import joeuncamp.dabombackend.domain.order.entity.Coupon;
import joeuncamp.dabombackend.domain.order.repository.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponAdminService {
    private final CouponJpaRepository couponJpaRepository;

    public List<CouponAdminDto.Response> getCoupons(){
        List<Coupon> coupons = couponJpaRepository.findAll();
        return coupons.stream()
                .map(CouponAdminDto.Response::new)
                .toList();
    }
}
