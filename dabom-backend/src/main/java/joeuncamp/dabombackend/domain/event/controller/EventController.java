package joeuncamp.dabombackend.domain.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.event.dto.EventDto;
import joeuncamp.dabombackend.domain.event.service.EventService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[5.Event]", description = "이벤트 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {
    private final EventService eventService;

    @Operation(summary="이벤트를 생성합니다.", description="")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/event")
    public ResponseEntity<Long> createEvent(@RequestBody EventDto.CreateRequest requestDto, @AuthenticationPrincipal Member member){
        requestDto.setMemberId(member.getId());
        Long response = eventService.createEvent(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
