package joeuncamp.dabombackend.domain.player.question.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.question.dto.QuestionDto;
import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.domain.player.question.repository.QuestionJpaRepository;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.common.PagingDto;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final MemberJpaRepository memberJpaRepository;
    private final UnitJpaRepository unitJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;

    /**
     * 강의 질문을 등록합니다.
     *
     * @param requestDto 강의 질문 정보
     * @return 생성된 질문의 아이디넘버
     */
    public Long createQuestion(QuestionDto.CreationRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Question question = requestDto.toEntity(member, unit);
        return questionJpaRepository.save(question).getId();
    }


    /**
     * 강의 내의 모든 질문을 조회합니다.
     *
     * @param requestDto 멤버, 조회할 강의
     * @param pageable   페이지정보
     * @return 전체 질문 목록
     */
    public PagingDto<QuestionDto.ShortResponse> getQuestions(QuestionDto.GetAllRequest requestDto, Pageable pageable) {
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Page<Question> page = questionJpaRepository.findByUnitOrderByCreatedTimeDesc(unit, pageable);
        List<QuestionDto.ShortResponse> questions = page.getContent().stream()
                .map(QuestionDto.ShortResponse::new)
                .toList();
        return new PagingDto<>(page.getNumber(), page.getTotalPages(), questions);
    }

    /**
     * 자신이 등록한 질문 목록을 조회합니다.
     *
     * @param requestDto 멤버, 조회할 강의
     * @param pageable   페이지정보
     * @return 등록한 질문 목록
     */
    public PagingDto<QuestionDto.ShortResponse> getMyQuestions(QuestionDto.GetAllRequest requestDto, Pageable pageable) {
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberExistException::new);
        Page<Question> page = questionJpaRepository.findByUnitAndMemberOrderByCreatedTimeDesc(unit, member, pageable);
        List<QuestionDto.ShortResponse> questions = page.getContent().stream()
                .map(QuestionDto.ShortResponse::new)
                .toList();
        return new PagingDto<>(page.getNumber(), page.getTotalPages(), questions);
    }

    /**
     * 질문을 상세 조회합니다.
     *
     * @param requestDto 멤버, 조회할 질문
     * @return 질문 상세 정보
     */
    public QuestionDto.Response getQuestion(QuestionDto.GetRequest requestDto) {
        Question question = questionJpaRepository.findById(requestDto.getQuestionId()).orElseThrow(CResourceNotFoundException::new);
        return new QuestionDto.Response(question);
    }

    /**
     * 질문을 수정합니다.
     * 작성자 본인만 수정할 수 있습니다.
     *
     * @param requestDto 멤버, 수정할 질문, 수정 항목
     */
    public void updateQuestion(QuestionDto.UpdateRequest requestDto) {
        Question question = questionJpaRepository.findById(requestDto.getQuestionId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!question.getMember().equals(member)) {
            throw new CAccessDeniedException("작성자 본인만 수정할 수 있습니다.");
        }
        question.update(requestDto.getTitle(), requestDto.getContent());
        questionJpaRepository.save(question);
    }

    /**
     * 질문을 삭제합니다.
     * 작성자 본인만 삭제할 수 있습니다.
     *
     * @param requestDto 멤버, 삭제할 질문
     */
    public void deleteQuestion(QuestionDto.GetRequest requestDto) {
        Question question = questionJpaRepository.findById(requestDto.getQuestionId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!question.getMember().equals(member)) {
            throw new CAccessDeniedException("작성자 본인만 삭제할 수 있습니다.");
        }
        questionJpaRepository.delete(question);
    }
}
