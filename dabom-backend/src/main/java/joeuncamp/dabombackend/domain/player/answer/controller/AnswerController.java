package joeuncamp.dabombackend.domain.player.answer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.answer.dto.AnswerDto;
import joeuncamp.dabombackend.domain.player.answer.service.AnswerService;
import joeuncamp.dabombackend.domain.player.question.dto.QuestionDto;
import joeuncamp.dabombackend.global.common.PagingDto;
import joeuncamp.dabombackend.global.common.SingleResponseDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "[4-3.Answer]", description = "답변 관련 API입니다.")
public class AnswerController {
    private final AnswerService answerService;

    @Operation(summary = "질문에 대한 답변을 등록합니다.", description = "수강생 및 크리에이터만 등록할 수 있습니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/questions/{questionId}/answers")
    public ResponseEntity<SingleResponseDto<Long>> createAnswer(@PathVariable Long questionId, @RequestBody AnswerDto.CreationRequest requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setQuestionId(questionId);
        requestDto.setMemberId(member.getId());
        SingleResponseDto<Long> responseDto = answerService.createAnswer(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "질문에 달린 모든 답변을 조회합니다.", description = "수강생 및 크리에이터만 등록할 수 있습니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/questions/{questionId}/answers")
    public ResponseEntity<PagingDto<AnswerDto.Response>> getAnswers(@PathVariable Long questionId, @ParameterObject Pageable pageable, @AuthenticationPrincipal Member member) {
        AnswerDto.GetRequest requestDto = new AnswerDto.GetRequest(member.getId(), questionId);
        PagingDto<AnswerDto.Response> responseDto = answerService.getAnswers(requestDto, pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
