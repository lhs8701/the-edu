package joeuncamp.dabombackend.domain.admin.dto;

import joeuncamp.dabombackend.domain.course.dto.CreatorStatusDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class StatusAdminDto {
    Long totalProfit;
    List<Map.Entry<Integer, List<Long>>> monthlyProfit;
    List<CreatorStatusDto> creatorStatusList;
}
