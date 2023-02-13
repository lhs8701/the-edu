package joeuncamp.dabombackend.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.dto.TicketDto;
import joeuncamp.dabombackend.domain.course.service.CourseTicketService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "[Order]", description = "구매 관련 API입니다.")
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final CourseTicketService courseTicketService;

    @Operation(summary="강좌 수강권 설정", description="수강권의 금액을 설정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/{courseId}/tickets")
    public ResponseEntity<Void> setTicket(@PathVariable Long courseId, @RequestBody TicketDto.Request requestDto, @AuthenticationPrincipal Member member){
        requestDto.setMemberId(member.getId());
        requestDto.setCourseId(courseId);
        courseTicketService.updatePrice(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary="강좌 수강권 조회", description="")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/courses/{courseId}/tickets")
    public ResponseEntity<List<TicketDto.Response>> getTickets(Long courseId){
        List<TicketDto.Response> responseDto = courseTicketService.getTickets(courseId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
