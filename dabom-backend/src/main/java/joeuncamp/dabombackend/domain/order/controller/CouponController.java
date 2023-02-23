package joeuncamp.dabombackend.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.dto.CouponDto;
import joeuncamp.dabombackend.domain.order.service.CouponService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "[5-2.Coupon]", description = "쿠폰 관련 API입니다.")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class CouponController {
    private final CouponService couponService;

    @Operation(summary="쿠폰코드로 쿠폰을 발급합니다.", description="")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/coupons/register")
    public ResponseEntity<Void> registerCoupon(@RequestBody @Valid CouponDto.RegisterRequest requestDto, @AuthenticationPrincipal Member member){
        requestDto.setMemberId(member.getId());
        couponService.registerCoupon(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary="사용한 쿠폰 조회", description="회원이 사용한 쿠폰 목록을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/coupons/used")
    public ResponseEntity<List<CouponDto.Response>> getMyUsedCoupons(@AuthenticationPrincipal Member member){
        List<CouponDto.Response> responseDto = couponService.getMyUsedCoupons(member.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary="미사용 쿠폰 조회", description="회원이 사용하지 않은 쿠폰 목록을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/coupons/unused")
    public ResponseEntity<List<CouponDto.Response>> getMyUnusedCoupons(@AuthenticationPrincipal Member member){
        List<CouponDto.Response> responseDto = couponService.getMyUnusedCoupons(member.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
