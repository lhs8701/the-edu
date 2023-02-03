package joeuncamp.dabombackend.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.dto.CreatorRequestDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.service.CreatorService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "[2-2.Creator]", description = "크리에이터 관련 API입니다.")
@RequiredArgsConstructor
@RequestMapping("/api")
public class CreatorController {
    private final CreatorService creatorService;

    @Operation(summary = "크리에이터 프로필 활성화", description = "회원이 크리에이터 기능을 수행할 수 있게 됩니다.")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/creators/activate")
    public ResponseEntity<Void> activateCreatorProfile(@RequestBody CreatorRequestDto requestDto) {
        creatorService.activateCreatorProfile(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
