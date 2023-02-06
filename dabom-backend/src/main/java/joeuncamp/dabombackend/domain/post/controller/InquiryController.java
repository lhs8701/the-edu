package joeuncamp.dabombackend.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.post.dto.InquiryDto;
import joeuncamp.dabombackend.domain.post.dto.ReviewDto;
import joeuncamp.dabombackend.domain.post.entity.Inquiry;
import joeuncamp.dabombackend.domain.post.service.InquiryService;
import joeuncamp.dabombackend.global.common.IdResponseDto;
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
@RequiredArgsConstructor
@Tag(name = "[3-5.Inquiry]", description = "문의사항 관련 API입니다.")
@RequestMapping("/api")
public class InquiryController {
    private final InquiryService inquiryService;


    @Operation(summary = "문의사항을 작성합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses/inquiries")
    public ResponseEntity<IdResponseDto> writeInquiry(@RequestBody InquiryDto.Request requestDto) {
        IdResponseDto responseDto = inquiryService.writeInquiry(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "강좌 내의 문의사항을 조회합니다.", description = "")
    @PreAuthorize("permitAll()")
    @GetMapping("/courses/{courseId}/inquiries")
    public ResponseEntity<List<InquiryDto.Response>> getReviews(@PathVariable Long courseId) {
        List<InquiryDto.Response> responseDto = inquiryService.getInquiries(courseId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "강좌의 문의사항을 수정합니다.", description = "작성자 본인만 수정할 수 있습니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/inquiries/{inquiryId}")
    public ResponseEntity<Long> updateReview(@PathVariable Long inquiryId, @RequestBody InquiryDto.UpdateRequest requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        requestDto.setInquiryId(inquiryId);
        Long response = inquiryService.updateInquiry(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "강좌의 문의사항을 삭제합니다.", description = "작성자 본인만 삭제할 수 있습니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/inquiries/{inquiryId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long inquiryId, @AuthenticationPrincipal Member member) {
        InquiryDto.DeleteRequest requestDto = new InquiryDto.DeleteRequest(member.getId(), inquiryId);
        inquiryService.deleteInquiry(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
