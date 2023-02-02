package joeuncamp.dabombackend.domain.player.question.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.question.dto.QuestionDto;
import joeuncamp.dabombackend.domain.player.question.service.QuestionService;
import joeuncamp.dabombackend.global.common.SingleResponseDto;
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
@Tag(name = "[4.Question]", description = "강의 질문 관련 API입니다.")
public class QuestionController {
    private final QuestionService questionService;

    @Operation(summary="강의에 대한 질문을 등록합니다.", description="")
    @Parameter(name = Header.JWT_HEADER, description="어세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/unit/{unitId}/questions")
    public ResponseEntity<SingleResponseDto<Long>> createQuestion(@PathVariable Long unitId, @RequestBody QuestionDto.CreationRequest requestDto, @AuthenticationPrincipal Member member){
        requestDto.setMemberId(member.getId());
        requestDto.setUnitId(unitId);
        SingleResponseDto<Long> responseDto = questionService.createQuestion(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
