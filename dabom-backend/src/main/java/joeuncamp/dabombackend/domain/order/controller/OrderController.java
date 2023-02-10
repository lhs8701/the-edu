package joeuncamp.dabombackend.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import joeuncamp.dabombackend.domain.course.dto.TicketDto;
import joeuncamp.dabombackend.domain.course.service.CourseTicketService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final CourseTicketService courseTicketService;

    @Operation(summary="강좌 수강권 조회", description="")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/courses/{courseId}/tickets")
    public ResponseEntity<List<TicketDto.Response>> getTickets(Long courseId){
        List<TicketDto.Response> responseDto = courseTicketService.getTickets(courseId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
