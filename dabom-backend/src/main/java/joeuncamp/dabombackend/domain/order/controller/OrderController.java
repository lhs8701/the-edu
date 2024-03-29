package joeuncamp.dabombackend.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import joeuncamp.dabombackend.domain.course.dto.TicketDto;
import joeuncamp.dabombackend.domain.course.service.TicketService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.domain.order.dto.OrderSheetDto;
import joeuncamp.dabombackend.domain.order.service.OrderChart;
import joeuncamp.dabombackend.domain.order.service.OrderSheetService;
import joeuncamp.dabombackend.domain.order.service.OrderSystem;
import joeuncamp.dabombackend.domain.order.service.RefundService;
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
@Tag(name = "[5-1.Order]", description = "구매 관련 API입니다.")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class OrderController {
    private final TicketService ticketService;
    private final OrderSheetService orderSheetService;
    private final OrderSystem orderSystem;
    private final OrderChart orderChart;
    private final RefundService refundService;

    @Operation(summary = "강좌 수강권 조회", description = "")
    @GetMapping("/courses/{courseId}/tickets")
    public ResponseEntity<TicketDto.Response> getTickets(@PathVariable Long courseId) {
        TicketDto.Response responseDto = ticketService.getTicket(courseId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "주문서 조회", description = "구매 전 주문서를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/purchase/items/{itemId}")
    public ResponseEntity<OrderSheetDto.Response> getOrderSheet(@PathVariable Long itemId, @AuthenticationPrincipal Member member) {
        OrderSheetDto.Request requestDto = new OrderSheetDto.Request(member.getId(), itemId);
        OrderSheetDto.Response responseDto = orderSheetService.getOrderSheet(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "주문 승인", description = "결제를 승인하여 완료합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/purchase/items/{itemId}")
    public ResponseEntity<Void> completeOrder(@PathVariable Long itemId, @RequestBody @Valid OrderDto.Request requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        requestDto.setItemId(itemId);
        orderSystem.makeOrder(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "주문 내역 조회", description = "회원의 주문 내역을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto.Response>> getOrderHistory(@AuthenticationPrincipal Member member) {
        List<OrderDto.Response> responseDto = orderChart.getOrderHistory(member.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "환불", description = "상품을 환불합니다. 정책에 따라 환불이 불가능할 수 있습니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/refund/orders/{orderId}")
    public ResponseEntity<Void> refund(@PathVariable String orderId, @AuthenticationPrincipal Member member) {
        refundService.refund(orderId, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
