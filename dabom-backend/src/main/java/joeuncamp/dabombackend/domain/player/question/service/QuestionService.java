package joeuncamp.dabombackend.domain.player.question.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.question.dto.QuestionDto;
import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.domain.player.question.repository.QuestionJpaRepository;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.common.SingleResponseDto;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final MemberJpaRepository memberJpaRepository;
    private final UnitJpaRepository unitJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;

    public SingleResponseDto<Long> createQuestion(QuestionDto.CreationRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Question question = requestDto.toEntity(member, unit);
        return new SingleResponseDto<>(questionJpaRepository.save(question).getId());
    }
}
