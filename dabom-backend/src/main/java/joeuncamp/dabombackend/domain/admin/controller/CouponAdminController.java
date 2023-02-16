package joeuncamp.dabombackend.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.admin.dto.CouponAdminDto;
import joeuncamp.dabombackend.domain.admin.service.CouponAdminService;
import joeuncamp.dabombackend.domain.order.dto.CouponDto;
import joeuncamp.dabombackend.domain.order.service.CouponGenerator;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "[0-9.Admin-Coupon]", description = "크리에이터 관리 API입니다.")
@RequestMapping("/admin-api")
@RequiredArgsConstructor
public class CouponAdminController {

    private final CouponGenerator couponGenerator;
    private final CouponAdminService couponAdminService;

    @Operation(summary = "쿠폰 생성", description = "쿠폰을 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/coupons/generate")
    public ResponseEntity<Void> generate(@RequestBody CouponDto.GenerateRequest requestDto) {
        couponGenerator.generate(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "쿠폰 발급", description = "관리자가 회원에게 쿠폰을 발급합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/coupons/{couponId}/issue")
    public ResponseEntity<Void> issue(@PathVariable Long couponId, @RequestBody CouponDto.IssueRequest requestDto) {
        requestDto.setCouponId(couponId);
        couponGenerator.issue(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "쿠폰 랜덤 코드 생성", description = "쿠폰의 랜덤 코드를 생성합니다. 회원은 랜덤 코드를 입력해 쿠폰을 스스로 발급할 수 있습니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/coupons/{couponId}/code")
    public ResponseEntity<Void> generateCouponCode(@PathVariable Long couponId) {
        couponGenerator.generateCouponCode(couponId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "모든 쿠폰 조회", description = "현재까지 발급한 모든 쿠폰을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/coupons")
    public ResponseEntity<List<CouponAdminDto.Response>> getCoupons() {
        List<CouponAdminDto.Response> responseDto = couponAdminService.getCoupons();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
