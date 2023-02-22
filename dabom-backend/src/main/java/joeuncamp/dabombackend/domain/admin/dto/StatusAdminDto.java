package joeuncamp.dabombackend.domain.admin.dto;

import joeuncamp.dabombackend.domain.course.dto.CreatorStatusDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StatusAdminDto {
    long totalProfit;
    List<CreatorStatusDto> creatorStatusList;
}
