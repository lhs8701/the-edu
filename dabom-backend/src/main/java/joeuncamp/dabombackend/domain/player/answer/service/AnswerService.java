package joeuncamp.dabombackend.domain.player.answer.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.answer.dto.AnswerDto;
import joeuncamp.dabombackend.domain.player.answer.entity.Answer;
import joeuncamp.dabombackend.domain.player.answer.repository.AnswerJpaRepository;
import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.domain.player.question.repository.QuestionJpaRepository;
import joeuncamp.dabombackend.global.common.PagingDto;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerJpaRepository answerJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final EnrollService enrollService;

    /**
     * 답변을 등록합니다.
     *
     * @param requestDto 답변 정보
     * @return 생성된 답변의 아이디넘버
     */
    public Long createAnswer(AnswerDto.CreationRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Question question = questionJpaRepository.findById(requestDto.getQuestionId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, question.getUnit().getCourse())) {
            throw new CAccessDeniedException("등록하지 않은 강좌입니다.");
        }
        Answer answer = requestDto.toEntity(member, question);
        return answerJpaRepository.save(answer).getId();
    }

    /**
     * 질문에 달린 답변을 모두 조회합니다.
     *
     * @param requestDto 조회할 질문
     * @param pageable   pageable
     * @return 답변 목록
     */
    public PagingDto<AnswerDto.Response> getAnswers(AnswerDto.GetRequest requestDto, Pageable pageable) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Question question = questionJpaRepository.findById(requestDto.getQuestionId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, question.getUnit().getCourse())) {
            throw new CAccessDeniedException("등록하지 않은 강좌입니다.");
        }
        Page<Answer> page = answerJpaRepository.findByQuestion(question, pageable);
        List<AnswerDto.Response> answers = page.getContent().stream()
                .map(AnswerDto.Response::new)
                .toList();
        return new PagingDto<>(page.getNumber(), page.getTotalPages(), answers);
    }


    /**
     * 답변을 수정합니다.
     *
     * @param requestDto 수정할 내용
     */
    public void updateAnswer(AnswerDto.UpdateRequest requestDto) {
        Answer answer = answerJpaRepository.findById(requestDto.getAnswerId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, answer.getQuestion().getUnit().getCourse())) {
            throw new CAccessDeniedException("작성자 본인만 수정할 수 있습니다.");
        }
        answer.update(requestDto.getContent());
        answerJpaRepository.save(answer);
    }

    /**
     * 답변을 삭제합니다.
     *
     * @param requestDto 회원, 삭제할 답변
     */
    public void deleteAnswer(AnswerDto.DeleteRequest requestDto) {
        Answer answer = answerJpaRepository.findById(requestDto.getAnswerId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, answer.getQuestion().getUnit().getCourse())) {
            throw new CAccessDeniedException("작성자 본인만 삭제할 수 있습니다.");
        }
        answerJpaRepository.delete(answer);
    }
}
