package joeuncamp.dabombackend.domain.creator.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.dto.CreatorStatusDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.course.service.ChapterService;
import joeuncamp.dabombackend.domain.course.service.TicketService;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.post.dto.ReplyDto;
import joeuncamp.dabombackend.domain.post.entity.Post;
import joeuncamp.dabombackend.domain.post.entity.Reply;
import joeuncamp.dabombackend.domain.post.repository.PostJpaRepository;
import joeuncamp.dabombackend.domain.post.repository.ReplyJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CNotCreatorException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorCourseService {
    private final MemberJpaRepository memberJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 12, 31, 0, 0, 0);
    private final ChapterService chapterService;
    private final EnrollJpaRepository enrollJpaRepository;
    private final TicketService ticketService;
    private final PostJpaRepository postJpaRepository;
    private final ReplyJpaRepository replyJpaRepository;

    /**
     * 강좌를 개설합니다. 크리에이터 프로필이 활성화되지 않은 경우, 예외가 발생합니다.
     * 개설 후, 크리에이터에게 강좌 등록 정보를 부여합니다.
     * 기본 챕터와 티켓이 생성됩니다.
     * 생성한 직후는 대기 상태입니다.
     *
     * @param requestDto 강좌 개설 정보
     * @return 개설된 강좌의 아이디넘버
     */
    public Long openCourse(CourseDto.CreationRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!member.isCreator()) {
            throw new CNotCreatorException();
        }
        CreatorProfile creator = member.getCreatorProfile();
        Course course = requestDto.toEntity(creator);
        courseJpaRepository.save(course);
        doDefaultSettings(member, course);
        return course.getId();
    }

    private void doDefaultSettings(Member member, Course course) {
        Enroll enroll = Enroll.builder()
                .course(course)
                .member(member)
                .build();
        enroll.setEndDate(MAX_DATE);
        enrollJpaRepository.save(enroll);
        chapterService.saveDefaultChapter(course);
        ticketService.createDefaultTickets(course);
    }

    /**
     * 크리에이터가 업로드한 강좌 목록을 조회합니다.
     *
     * @param memberId 크리에이터
     * @return 업로드한 강좌 목록
     */
    public List<CourseDto.CreatorResponse> getUploadedCourses(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        if (!member.isCreator()) {
            throw new CAccessDeniedException("크리에이터만 이용할 수 있는 기능입니다.");
        }
        return courseJpaRepository.findByCreatorProfile(member.getCreatorProfile()).stream()
                .map(CourseDto.CreatorResponse::new)
                .toList();
    }


    /**
     * 게시물에 댓글을 작성합니다.
     *
     * @param requestDto 댓글 작성 DTO
     * @return 작성한 댓글의 아이디넘버
     */
    public Long writeReply(ReplyDto.WriteRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Post post = postJpaRepository.findById(requestDto.getPostId()).orElseThrow(CResourceNotFoundException::new);
        if (!member.isCreator() || !post.getCourse().getCreatorProfile().equals(member.getCreatorProfile())) {
            throw new CAccessDeniedException("크리에이터만 이용할 수 있는 기능입니다.");
        }
        Reply reply = requestDto.toEntity(member.getCreatorProfile(), post);
        return replyJpaRepository.save(reply).getId();
    }

    /**
     * 댓글을 수정합니다.
     *
     * @param requestDto 댓글 수정 DTO
     */
    public void updateReply(ReplyDto.UpdateRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Reply reply = replyJpaRepository.findById(requestDto.getReplyId()).orElseThrow(CResourceNotFoundException::new);
        if (!member.isCreator() || !reply.getCreator().equals(member.getCreatorProfile())) {
            throw new CAccessDeniedException("크리에이터만 이용할 수 있는 기능입니다.");
        }
        reply.update(requestDto.getContent());
        replyJpaRepository.save(reply);
    }


    /**
     * 댓글을 삭제합니다.
     *
     * @param  memberId 삭제할 댓글
     * @param replyId 크리에이터
     */
    @Transactional
    public void deleteReply(Long memberId, Long replyId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        Reply reply = replyJpaRepository.findById(replyId).orElseThrow(CResourceNotFoundException::new);
        if (!member.isCreator() || !reply.getCreator().equals(member.getCreatorProfile())) {
            throw new CAccessDeniedException("크리에이터만 이용할 수 있는 기능입니다.");
        }
        replyJpaRepository.deleteById(replyId);
    }
}
