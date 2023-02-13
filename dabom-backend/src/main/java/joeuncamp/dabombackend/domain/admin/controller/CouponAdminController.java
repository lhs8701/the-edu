package joeuncamp.dabombackend.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.order.dto.CouponDto;
import joeuncamp.dabombackend.domain.order.service.CouponGenerator;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "[0-9.Admin-Coupon]", description = "크리에이터 관리 API입니다.")
@RequestMapping("/admin-api")
@RequiredArgsConstructor
public class CouponAdminController {

    private final CouponGenerator couponGenerator;

    @Operation(summary = "쿠폰 생성", description = "쿠폰을 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/coupon/generate")
    public ResponseEntity<Void> generate(@RequestBody CouponDto.GenerateRequest requestDto) {
        couponGenerator.generate(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "쿠폰 발급", description = "회원에게 쿠폰을 발급합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/coupon/{couponId}/issue")
    public ResponseEntity<Void> issue(@PathVariable Long couponId, @RequestBody CouponDto.IssueRequest requestDto) {
        requestDto.setCouponId(couponId);
        couponGenerator.issue(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
