package joeuncamp.dabombackend.domain.player.answer.service;

import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.CreatorService;
import joeuncamp.dabombackend.domain.player.answer.dto.AnswerDto;
import joeuncamp.dabombackend.domain.player.answer.entity.Answer;
import joeuncamp.dabombackend.domain.player.answer.repository.AnswerJpaRepository;
import joeuncamp.dabombackend.domain.player.question.dto.QuestionDto;
import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.domain.player.question.repository.QuestionJpaRepository;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.common.PagingDto;
import joeuncamp.dabombackend.global.common.SingleResponseDto;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerJpaRepository answerJpaRepository;
    private final CreatorProfileJpaRepository creatorProfileJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;


    /**
     * 답변을 등록합니다.
     *
     * @param requestDto 답변 정보
     * @return 생성된 답변의 아이디넘버
     */
    public SingleResponseDto<Long> createAnswer(AnswerDto.CreationRequest requestDto) {
        CreatorProfile creator = creatorProfileJpaRepository.findByMember(requestDto.getMember()).orElseThrow(CResourceNotFoundException::new);
        Question question = questionJpaRepository.findById(requestDto.getQuestionId()).orElseThrow(CResourceNotFoundException::new);
        if (!question.getUnit().getCourse().getCreatorProfile().equals(creator)) {
            throw new CResourceNotFoundException();
        }
        Answer answer = requestDto.toEntity(creator, question);
        return new SingleResponseDto<>(answerJpaRepository.save(answer).getId());
    }

    public PagingDto<AnswerDto.Response> getAnswers(AnswerDto.GetRequest requestDto, Pageable pageable) {
        CreatorProfile creator = creatorProfileJpaRepository.findByMember(requestDto.getMember()).orElseThrow(CResourceNotFoundException::new);
        Question question = questionJpaRepository.findById(requestDto.getQuestionId()).orElseThrow(CResourceNotFoundException::new);

        Page<Answer> page = answerJpaRepository.findByQuestion(question, pageable);
        List<AnswerDto.Response> answers = page.getContent().stream()
                .map(AnswerDto.Response::new)
                .toList();
        return new PagingDto<>(page.getNumber(), page.getTotalPages(), answers);
    }
}
