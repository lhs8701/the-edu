package joeuncamp.dabombackend.domain.player.view.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.view.dto.ViewDto;
import joeuncamp.dabombackend.domain.player.view.service.ViewService;
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
@Tag(name = "[4.View]", description = "강의 시청 기록 관련 API입니다.")
public class ViewController {
    private final ViewService viewService;

    @Operation(summary = "마지막으로 강의를 시청한 지점을 저장합니다.", description = "시간은 초단위로 저장됩니다. ")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/units/{unitId}/view")
    public ResponseEntity<Void> saveView(@PathVariable Long unitId, @RequestBody ViewDto.SaveRequest requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        requestDto.setUnitId(unitId);
        viewService.saveView(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "강의 시청 기록을 불러옵니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/units/{unitId}/view")
    public ResponseEntity<SingleResponseDto<Double>> getView(@PathVariable Long unitId, @AuthenticationPrincipal Member member) {
        ViewDto.GetRequest requestDto = new ViewDto.GetRequest(member.getId(), unitId);
        SingleResponseDto<Double> responseDto = viewService.getView(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
