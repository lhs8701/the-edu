package joeuncamp.dabombackend.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingDto<T> {
    @Schema(description = "현재 페이지", example = "0")
    int page;
    @Schema(description = "전체 페이지", example = "20")
    int totalPage;
    @Schema(description = "목록")
    List<T> list;
}
