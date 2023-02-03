package joeuncamp.dabombackend.domain.player.record.service;

import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.record.dto.RecordDto;
import joeuncamp.dabombackend.domain.player.record.repository.RecordRedisRepository;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordRedisRepository recordRedisRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    /**
     * 강의 시청 기록을 저장합니다.
     *
     * @param requestDto 회원, 강의, 시간
     */
    public void saveRecord(RecordDto.SaveRequest requestDto) {
        validateId(requestDto.getMemberId(), requestDto.getUnitId());
        String memberId = String.valueOf(requestDto.getMemberId());
        String unitId = String.valueOf(requestDto.getUnitId());
        String time = String.valueOf(requestDto.getTime());
        recordRedisRepository.saveRecord(memberId, unitId, time);
    }

    /**
     * 마지막으로 시청한 지점을 반환합니다.
     *
     * @param requestDto 회원, 강의
     * @return 시청 정보
     */
    public Double getView(RecordDto.GetRequest requestDto) {
        validateId(requestDto.getMemberId(), requestDto.getUnitId());
        String memberId = String.valueOf(requestDto.getMemberId());
        String unitId = String.valueOf(requestDto.getUnitId());
        String time = recordRedisRepository.getTimeFromRecord(memberId, unitId);
        if (time == null) {
            time = "0";
        }
        return Double.parseDouble(time);
    }

    private void validateId(Long memberId, Long unitId) {
        if (memberJpaRepository.findById(memberId).isEmpty() || unitJpaRepository.findById(unitId).isEmpty()) {
            throw new CResourceNotFoundException();
        }
    }
}
