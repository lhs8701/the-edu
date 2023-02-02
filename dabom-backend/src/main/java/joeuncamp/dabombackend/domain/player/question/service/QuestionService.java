package joeuncamp.dabombackend.domain.player.question.service;

import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.question.dto.QuestionDto;
import joeuncamp.dabombackend.domain.player.question.entity.Question;
import joeuncamp.dabombackend.domain.player.question.repository.QuestionJpaRepository;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.common.PagingDto;
import joeuncamp.dabombackend.global.common.SingleResponseDto;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public SingleResponseDto<Long> createQuestion(QuestionDto.CreationRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Question question = requestDto.toEntity(member, unit);
        return new SingleResponseDto<>(questionJpaRepository.save(question).getId());
    }


    /**
     * 강의 내의 모든 질문을 조회합니다.
     * @param requestDto 유닛 아이디넘버, 멤버 아이디넘버
     * @param pageable 페이지정보
     * @return 전체 질문 목록
     */
    public PagingDto<QuestionDto.ShortResponse> getQuestions(QuestionDto.GetAllRequest requestDto, Pageable pageable) {
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Page<Question> page = questionJpaRepository.findByUnit(unit, pageable);
        List<QuestionDto.ShortResponse> questions = page.getContent().stream()
                .map(QuestionDto.ShortResponse::new)
                .toList();
        return new PagingDto<>(page.getNumber(), page.getTotalPages(), questions);
    }

    /**
     * 질문을 상세 조회합니다.
     * @param requestDto 멤버 아이디넘버, 질문 아이디넘버
     * @return 질문 상세 정보
     */
    public QuestionDto.Response getQuestion(QuestionDto.GetRequest requestDto){
        Question question = questionJpaRepository.findById(requestDto.getQuestionId()).orElseThrow(CResourceNotFoundException::new);
        return new QuestionDto.Response(question);
    }

}
