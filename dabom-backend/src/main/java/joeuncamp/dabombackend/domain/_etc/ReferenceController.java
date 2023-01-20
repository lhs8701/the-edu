package joeuncamp.dabombackend.domain._etc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.course.dto.CategoryResponseDto;
import joeuncamp.dabombackend.global.constant.CategoryGroup;
import joeuncamp.dabombackend.global.error.ErrorCode;
import joeuncamp.dabombackend.global.error.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "[0.Reference]", description = "참고용 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReferenceController {

    @Operation(summary="에러 코드를 조회합니다.", description="")
    @PreAuthorize("permitAll()")
    @GetMapping("/ref/error-code")
    @ResponseBody
    public List<ErrorResponseDto> getErrorCode(){
        return Arrays.stream(ErrorCode.values()).map(ErrorResponseDto::new).collect(Collectors.toList());
    }

    @Operation(summary = "모든 카테고리를 조회합니다.", description = "")
    @PreAuthorize("permitAll()")
    @GetMapping("/ref/category")
    @ResponseBody
    public List<CategoryResponseDto> getAllCategory() {
        return Arrays.stream(CategoryGroup.values()).map(CategoryResponseDto::new).collect(Collectors.toList());
    }
}
