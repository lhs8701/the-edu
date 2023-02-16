package joeuncamp.dabombackend.domain.creator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.creator.dto.CreatorDto;
import joeuncamp.dabombackend.domain.creator.service.CreatorCourseService;
import joeuncamp.dabombackend.domain.creator.service.CreatorService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.creator.service.CreatorActivator;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "[2-2.Creator]", description = "크리에이터 관련 API입니다.")
@RequiredArgsConstructor
@RequestMapping("/api")
public class CreatorController {
    private final CreatorActivator creatorActivator;
    private final CreatorCourseService creatorCourseService;

    @Operation(summary = "강좌를 개설합니다.", description = "강좌 개설은 크리에이터 프로필을 활성화한 회원만 가능합니다. \n 개설된 강좌의 아이디넘버가 반환됩니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "AccessToken", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/courses")
    public ResponseEntity<Long> openCourse(@AuthenticationPrincipal Member member, @RequestBody CourseDto.CreationRequest requestDto) {
        requestDto.setMemberId(member.getId());
        Long response = creatorCourseService.openCourse(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "업로드한 강좌 조회", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/creators/me/courses")
    public ResponseEntity<List< CourseDto.ShortResponse>> getUploadedCourses(@AuthenticationPrincipal Member member) {
        List< CourseDto.ShortResponse> responseDto =  creatorCourseService.getUploadedCourses(member.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "크리에이터 대기", description = "회원을 크리에이터 대기 상태로 만듭니다. 이후 관리자의 승인을 통해 최종적으로 크리에이터 권한을 획득할 수 있습니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/creators/me/standby")
    public ResponseEntity<Void> standByCreator(@RequestBody CreatorDto.StandByRequest requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        creatorActivator.standByMember(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
