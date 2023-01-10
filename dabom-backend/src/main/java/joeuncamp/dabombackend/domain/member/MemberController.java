package joeuncamp.dabombackend.domain.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.domain.member.service.MemberManageService;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "[Member]", description = "회원과 관련된 API입니다.")
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberManageService memberManageService;

    @PreAuthorize("permitAll()")
    @Operation(summary = "회원 생성", description = "회원을 생성합니다.")
    @PostMapping("")
    public void createMember(@Valid MemberCreationRequestDto requestDto) {
        memberManageService.createMember(requestDto);
    }

    @Parameter(name = Header.JWT_HEADER, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQG5hdmVyLmNvbSIsImFjY291bnQiOiJ1c2VyQG5hdmVyLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNjczMzAzMzY5LCJleHAiOjE3MDQ4MzkzNjl9.s_HnINVN5QTNdmCNdRJ0XBx2jISxuvvywJB_tA6SA9Q")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "회원 단일 조회", description = "회원을 한명을 조회합니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(@PathVariable Long memberId) {
        return new ResponseEntity<>(memberManageService.getMember(memberId), HttpStatus.OK);
    }
}

