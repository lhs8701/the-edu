package joeuncamp.dabombackend.domain.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "[Member]", description = "회원과 관련된 API입니다.")
@RequestMapping("/members")
public class MemberController {
    @Operation(summary = "회원 생성", description = "회원을 생성합니다.")
    @PostMapping("")
    public void createMember(){

    }

    @Operation(summary = "회원 단일 조회", description = "회원을 한명을 조회합니다.")
    @GetMapping("")
    public void getMember(){

    }
}
