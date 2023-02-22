package joeuncamp.dabombackend.domain.admin.service;

import joeuncamp.dabombackend.domain.admin.dto.StatusAdminDto;
import joeuncamp.dabombackend.domain.course.dto.CreatorStatusDto;
import joeuncamp.dabombackend.domain.creator.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.creator.service.CreatorStatusService;
import joeuncamp.dabombackend.domain.order.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusAdminService {
    private final CreatorStatusService creatorStatusService;
    private final CreatorProfileJpaRepository creatorProfileJpaRepository;
    private final OrderJpaRepository orderJpaRepository;

    /**
     * 서비스 전체 수익 및 크리에이터별 수익 상태를 조회합니다.
     *
     * @return 수익 상태
     */
    public StatusAdminDto getServiceStatus() {
        long totalProfit = orderJpaRepository.findProfitInTicket();
        List<CreatorStatusDto> creatorStatusList = creatorProfileJpaRepository.findAll()
                .stream()
                .map(creatorStatusService::getResponse)
                .toList();
        return new StatusAdminDto(totalProfit, creatorStatusList);
    }
}
