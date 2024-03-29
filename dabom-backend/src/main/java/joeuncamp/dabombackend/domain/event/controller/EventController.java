package joeuncamp.dabombackend.domain.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

import java.util.List;

@Tag(name = "[5.Event]", description = "이벤트 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {
    private final EventService eventService;

    @Operation(summary="이벤트를 생성합니다.", description="")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/events")
    public ResponseEntity<Long> createEvent(@RequestBody @Valid EventDto.CreateRequest requestDto, @AuthenticationPrincipal Member member){
        requestDto.setMemberId(member.getId());
        Long response = eventService.createEvent(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary="이벤트를 조회합니다.", description="")
    @PreAuthorize("permitAll()")
    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventDto.Response> getEvent(@PathVariable Long eventId){
        EventDto.Response responseDto = eventService.getEvent(eventId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary="진행 중인 이벤트 목록을 조회합니다.", description="")
    @PreAuthorize("permitAll()")
    @GetMapping("/events/ongoing")
    public ResponseEntity<List<EventDto.ShortResponse>> getOngoingEvent(){
        List<EventDto.ShortResponse> responseDto = eventService.getOngoingEvents();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary="종료된 이벤트 목록을 조회합니다.", description="")
    @PreAuthorize("permitAll()")
    @GetMapping("/events/closed")
    public ResponseEntity<List<EventDto.ShortResponse>> getClosedEvent(){
        List<EventDto.ShortResponse> responseDto = eventService.getClosedEvent();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary="이벤트를 수정합니다.", description="")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/events/{eventId}")
    public ResponseEntity<Void> updateEvent(@PathVariable Long eventId, EventDto.UpdateRequest requestDto){
        requestDto.setEventId(eventId);
        eventService.updateEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary="이벤트를 삭제합니다.", description="")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
