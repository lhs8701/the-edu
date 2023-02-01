package joeuncamp.dabombackend.domain.player.play.service;

import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.unit.dto.UnitDto;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final UnitJpaRepository unitJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    private final EnrollService enrollService;

    /**
     * 강의를 재생합니다.
     *
     * @param unitDto 재생할 강의 아이디넘버
     * @return 강의 세부 정보
     */
    public UnitDto.Response playUnit(UnitDto unitDto) {
        Unit unit = unitJpaRepository.findById(unitDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(unitDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, unit.getCourse())){
            throw new CAccessDeniedException();
        }
        return new UnitDto.Response(unit);
    }
}
