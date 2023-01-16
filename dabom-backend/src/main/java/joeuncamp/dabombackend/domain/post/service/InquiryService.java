package joeuncamp.dabombackend.domain.post.service;

import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.post.dto.InquiryDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.post.entity.Inquiry;
import joeuncamp.dabombackend.domain.post.repository.InquiryJpaRepository;
import joeuncamp.dabombackend.global.common.IdResponseDto;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryJpaRepository inquiryJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final CourseJpaRepository courseJpaRepository;

    /**
     * 문의사항을 작성합니다.
     *
     * @param requestDto 문의사항 작성 DTO
     * @return 작성된 문의사항의 아이디넘버
     */
    public IdResponseDto writeInquiry(InquiryDto.Request requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        Long savedId = createAndSaveInquiry(requestDto, member, course);
        return new IdResponseDto(savedId);
    }

    private Long createAndSaveInquiry(InquiryDto.Request requestDto, Member member, Course course) {
        Inquiry inquiry = requestDto.toEntity(member, course);
        return inquiryJpaRepository.save(inquiry).getId();
    }


    /**
     * 강좌의 문의사항 목록을 조회합니다.
     *
     * @param courseId 강좌 아이디넘버
     * @return 문의사항 목록
     */
    public List<InquiryDto.Response> getInquiries(Long courseId) {
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);
        List<Inquiry> inquiries = inquiryJpaRepository.findAllByCourse(course);
        return inquiries.stream().map(InquiryDto.Response::new).toList();
    }

}
