package joeuncamp.dabombackend.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.Getter;

@Getter
public class UnitDefaultResponse implements UnitResponse{
    @Schema(description = "유닛 아이디넘버")
    Long unitId;
    @Schema(description = "강의 제목", example = ExampleValue.Unit.TITLE)
    String title;
    public UnitDefaultResponse(Unit unit){
        this.unitId = unit.getId();
        this.title = unit.getTitle();
    }
}
