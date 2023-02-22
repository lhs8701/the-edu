package joeuncamp.dabombackend.domain.admin.service;

import joeuncamp.dabombackend.domain.admin.dto.StatusAdminDto;
import joeuncamp.dabombackend.domain.course.dto.CreatorStatusDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.creator.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.creator.service.CreatorStatusService;
import joeuncamp.dabombackend.domain.order.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Long totalProfit = orderJpaRepository.findTotalProfitInTicket();
        Map<Integer, List<Long>> monthlyProfit = getMonthlyProfit();
        List<CreatorStatusDto> creatorStatusList = creatorProfileJpaRepository.findAll()
                .stream()
                .map(creatorStatusService::getResponse)
                .toList();
        return new StatusAdminDto(totalProfit, monthlyProfit, creatorStatusList);
    }

    private Map<Integer, List<Long>> getMonthlyProfit() {
        LocalDate openedDate = LocalDate.of(2020, 1, 1);
        LocalDate startDate = YearMonth.from(openedDate).atDay(1);
        LocalDate endDate = YearMonth.from(openedDate).atEndOfMonth();
        Map<Integer, List<Long>> map = new HashMap<>();
        while (startDate.isBefore(LocalDate.now())) {
            if (!map.containsKey(startDate.getYear())) {
                map.put(startDate.getYear(), new ArrayList<>());
            }
            Long monthlyProfit = orderJpaRepository.findProfitInTicketInDuration(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
            map.get(startDate.getYear()).add(monthlyProfit);
            startDate = startDate.plusMonths(1);
            endDate = YearMonth.from(startDate).atEndOfMonth();
        }
        return map;
    }
}
