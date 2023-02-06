package joeuncamp.dabombackend.domain.course.dto;

import joeuncamp.dabombackend.domain.unit.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NextUnitInfo {
    Long unitId;
    String title;
    double time;

    public NextUnitInfo(Unit unit, double time) {
        this.unitId = unit.getId();
        this.title = unit.getTitle();
        this.time = time;
    }
}
