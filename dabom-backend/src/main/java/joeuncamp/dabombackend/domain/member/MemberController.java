package joeuncamp.dabombackend.domain.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.domain.member.service.MemberManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "[Member]", description = "회원과 관련된 API입니다.")
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberManageService memberManageService;

    @Operation(summary = "회원 생성", description = "회원을 생성합니다.")
    @PostMapping("")
    public void createMember(@Valid MemberCreationRequestDto requestDto){
        memberManageService.createMember(requestDto);
    }

    @Operation(summary = "회원 단일 조회", description = "회원을 한명을 조회합니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(@PathVariable Long memberId){
        return new ResponseEntity<>(memberManageService.getMember(memberId), HttpStatus.OK);
    }
}
