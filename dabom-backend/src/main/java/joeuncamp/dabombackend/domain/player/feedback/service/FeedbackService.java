package joeuncamp.dabombackend.domain.player.feedback.service;

import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.feedback.dto.FeedbackDto;
import joeuncamp.dabombackend.domain.player.feedback.entity.Feedback;
import joeuncamp.dabombackend.domain.player.feedback.repository.FeedbackJpaRepository;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CMemberNotFoundException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackJpaRepository feedbackJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final UnitJpaRepository unitJpaRepository;
    private final EnrollService enrollService;

    /**
     * 강의에 대한 피드백을 남깁니다.
     * 가장 최근에 남긴 피드백 하나만 기록됩니다.
     *
     * @param requestDto 회원, 강의, 코멘트, 좋아요
     * @return 생성된 피드백 아이디넘버
     */
    public Long doFeedback(FeedbackDto.CreateRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberNotFoundException::new);
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, unit.getCourse())){
            throw new CAccessDeniedException();
        }
        Feedback feedback = feedbackJpaRepository.findByMemberAndUnit(member, unit)
                .orElseGet(() -> new Feedback(member, unit));
        feedback.update(requestDto.getComment(), requestDto.getThumbsUp());
        return feedbackJpaRepository.save(feedback).getId();
    }

    /**
     * 강의 피드백을 조회합니다.
     *
     * @param requestDto 회원, 강의
     * @return 피드백 내용
     */
    public FeedbackDto.Response getFeedback(FeedbackDto.GetRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberNotFoundException::new);
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Feedback feedback = feedbackJpaRepository.findByMemberAndUnit(member, unit).orElseThrow(CResourceNotFoundException::new);
        return new FeedbackDto.Response(feedback);
    }
}
