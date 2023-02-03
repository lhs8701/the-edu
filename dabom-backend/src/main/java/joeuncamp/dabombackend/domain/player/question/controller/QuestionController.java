package joeuncamp.dabombackend.domain.player.question.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.question.dto.QuestionDto;
import joeuncamp.dabombackend.domain.player.question.service.QuestionService;
import joeuncamp.dabombackend.global.common.PagingDto;
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
@Tag(name = "[4-2.Question]", description = "강의 질문 관련 API입니다.")
public class QuestionController {
    private final QuestionService questionService;

    @Operation(summary = "강의에 대한 질문을 등록합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/units/{unitId}/questions")
    public ResponseEntity<Long> createQuestion(@PathVariable Long unitId, @RequestBody QuestionDto.CreationRequest requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        requestDto.setUnitId(unitId);
        Long response = questionService.createQuestion(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "강의 내의 모든 질문을 조회합니다.", description = "등록순으로 정렬합니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/units/{unitId}/questions")
    public ResponseEntity<PagingDto<QuestionDto.ShortResponse>> getQuestions(@PathVariable Long unitId, @ParameterObject Pageable pageable, @AuthenticationPrincipal Member member) {
        QuestionDto.GetAllRequest requestDto = new QuestionDto.GetAllRequest(member.getId(), unitId);
        PagingDto<QuestionDto.ShortResponse> responseDto = questionService.getQuestions(requestDto, pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "자신이 등록한 질문을 조회합니다.", description = "등록순으로 정렬합니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/units/{unitId}/questions/mine")
    public ResponseEntity<PagingDto<QuestionDto.ShortResponse>> getMyQuestions(@PathVariable Long unitId, @ParameterObject Pageable pageable, @AuthenticationPrincipal Member member) {
        QuestionDto.GetAllRequest requestDto = new QuestionDto.GetAllRequest(member.getId(), unitId);
        PagingDto<QuestionDto.ShortResponse> responseDto = questionService.getMyQuestions(requestDto, pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @Operation(summary = "질문을 상세 조회합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/units/questions/{questionId}")
    public ResponseEntity<QuestionDto.Response> getQuestion(@PathVariable Long questionId, @AuthenticationPrincipal Member member) {
        QuestionDto.GetRequest requestDto = new QuestionDto.GetRequest(member.getId(), questionId);
        QuestionDto.Response responseDto = questionService.getQuestion(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "질문을 수정합니다.", description = "작성자 본인만 수정할 수 있습니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/units/questions/{questionId}")
    public ResponseEntity<Void> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionDto.UpdateRequest requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        requestDto.setQuestionId(questionId);
        questionService.updateQuestion(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "질문을 삭제합니다.", description = "작성자 본인만 삭제할 수 있습니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/units/questions/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId, @AuthenticationPrincipal Member member) {
        QuestionDto.GetRequest requestDto = new QuestionDto.GetRequest(member.getId(), questionId);
        questionService.deleteQuestion(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
