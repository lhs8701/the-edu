package joeuncamp.dabombackend.domain.player.feedback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.feedback.dto.FeedbackDto;
import joeuncamp.dabombackend.domain.player.feedback.entity.Feedback;
import joeuncamp.dabombackend.domain.player.feedback.service.FeedbackService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "[4-5.Feedback]", description = "강의 피드백 관련 API입니다.")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Operation(summary="강의 피드백하기", description="피드백을 남깁니다.\n가장 최근 피드백 하나만 기록됩니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/feedback/unit/{unitId}")
    public ResponseEntity<Long> doFeedback(@PathVariable Long unitId, FeedbackDto.CreateRequest requestDto, @AuthenticationPrincipal Member member){
        requestDto.setMemberId(member.getId());
        requestDto.setUnitId(unitId);
        Long response = feedbackService.doFeedback(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary="강의 피드백 조회", description="피드백을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/feedback/unit/{unitId}")
    public ResponseEntity<FeedbackDto.Response> getFeedback(@PathVariable Long unitId, @AuthenticationPrincipal Member member){
        FeedbackDto.GetRequest requestDto = new FeedbackDto.GetRequest(member.getId(), unitId);
        FeedbackDto.Response responseDto = feedbackService.getFeedback(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
