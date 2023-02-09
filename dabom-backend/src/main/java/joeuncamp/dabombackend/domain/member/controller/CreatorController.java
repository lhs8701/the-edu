package joeuncamp.dabombackend.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.dto.CreatorDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.service.CreatorActivator;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "[2-2.Creator]", description = "크리에이터 관련 API입니다.")
@RequiredArgsConstructor
@RequestMapping("/api")
public class CreatorController {
    private final CreatorActivator creatorActivator;

    @Operation(summary = "크리에이터 대기", description = "회원을 크리에이터 대기 상태로 만듭니다. 이후 관리자의 승인을 통해 최종적으로 크리에이터 권한을 획득할 수 있습니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/creator/standby")
    public ResponseEntity<Void> standByCreator(@AuthenticationPrincipal Member member) {
        CreatorDto.StandByRequest requestDto = new CreatorDto.StandByRequest(member.getId());
        creatorActivator.standByMember(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "크리에이터 활성화", description = "회원의 크리에이터 기능을 활성화합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/creator/activate/members/{memberId}")
    public ResponseEntity<Void> activateCreator(@PathVariable Long memberId) {
        creatorActivator.activateCreator(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
