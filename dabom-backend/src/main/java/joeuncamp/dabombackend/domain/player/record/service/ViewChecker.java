package joeuncamp.dabombackend.domain.player.record.service;

import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.record.dto.ViewDto;
import joeuncamp.dabombackend.domain.player.record.entity.View;
import joeuncamp.dabombackend.domain.player.record.repository.ViewJpaRepository;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewChecker {
    private final ViewJpaRepository viewJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final UnitJpaRepository unitJpaRepository;
    private final EnrollService enrollService;

    /**
     * 해당 강의를 시청 완료 처리합니다.
     *
     * @param requestDto 회원, 강의
     */
    public void completeUnit(ViewDto.CompleteRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, unit.getCourse())){
            throw new CAccessDeniedException();
        }
        View view = View.builder()
                .member(member)
                .unit(unit)
                .build();
        viewJpaRepository.save(view);
    }
}
