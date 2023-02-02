package joeuncamp.dabombackend.domain.player.view.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.player.view.dto.ViewDto;
import joeuncamp.dabombackend.domain.player.view.service.ViewService;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "[View]", description = "강의 시청 기록 관련 API입니다.")
public class ViewController {
    private final ViewService viewService;

    @Operation(summary = "마지막으로 강의를 시청한 지점을 저장합니다.", description = "")
    @Parameter(name = Header.JWT_HEADER, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/view/save")
    public ResponseEntity<Void> saveView(@RequestBody ViewDto.SaveRequest requestDto) {
        viewService.saveView(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
