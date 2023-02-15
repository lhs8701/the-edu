package joeuncamp.dabombackend.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.dto.TicketDto;
import joeuncamp.dabombackend.domain.course.service.CourseTicketService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.domain.order.service.OrderSheetService;
import joeuncamp.dabombackend.domain.order.service.OrderService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import joeuncamp.dabombackend.util.tossapi.dto.ConfirmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "[5.Order]", description = "구매 관련 API입니다.")
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final CourseTicketService courseTicketService;
    private final OrderService orderService;
    private final OrderSheetService orderSheetService;

    @Operation(summary = "강좌 수강권 설정", description = "수강권의 금액을 설정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/{courseId}/tickets")
    public ResponseEntity<Void> setTicket(@PathVariable Long courseId, @RequestBody TicketDto.Request requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        requestDto.setCourseId(courseId);
        courseTicketService.updatePrice(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "강좌 수강권 조회", description = "")
    @GetMapping("/courses/{courseId}/tickets")
    public ResponseEntity<List<TicketDto.Response>> getTickets(@PathVariable Long courseId) {
        List<TicketDto.Response> responseDto = courseTicketService.getTickets(courseId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "주문서 조회", description = "구매 전 주문서를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/purchase/items/{itemId}")
    public ResponseEntity<OrderDto.Response> getOrderSheet(@PathVariable Long itemId, @AuthenticationPrincipal Member member) {
        OrderDto.StatusRequest requestDto = new OrderDto.StatusRequest(member.getId(), itemId);
        OrderDto.Response responseDto = orderSheetService.getOrderSheet(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "주문 승인", description = "결제를 승인하여 완료합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/purchase/items/{itemId}")
    public ResponseEntity<OrderDto.Response> completeOrder(@PathVariable Long itemId, @RequestBody OrderDto.Request requestDto, @RequestBody ConfirmRequest confirmRequest, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        requestDto.setItemId(itemId);
        orderService.makeOrder(requestDto, confirmRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
